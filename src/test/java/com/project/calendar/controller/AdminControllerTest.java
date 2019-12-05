package com.project.calendar.controller;

import com.project.calendar.configuration.LoginSuccessHandler;
import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Hall;
import com.project.calendar.exception.EntityNotFoundException;
import com.project.calendar.exception.ExpositionAlreadyExistException;
import com.project.calendar.service.ExpositionService;
import com.project.calendar.service.HallService;
import com.project.calendar.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

import static com.project.calendar.MockData.*;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
@WithMockUser(username = "admin@gmail.com", authorities = "ADMIN")
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HallService hallService;

    @MockBean
    private ExpositionService expositionService;

    @MockBean
    private UserService userService;

    @MockBean
    private LoginSuccessHandler loginSuccessHandler;

    @Test
    public void addExpositionPage() throws Exception {
        when(hallService.showAll()).thenReturn(emptyList());

        final ModelAndView modelAndView = mockMvc.perform(get("/admin/add-exposition"))
                .andExpect(view().name("admin-add-exposition"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        assertThat(model.get("halls"), is(emptyList()));
        assertThat(model.get("exposition"), is(notNullValue()));
    }

    @Test
    public void addExpositionShouldAddExposition() throws Exception {
        when(hallService.showById(anyLong())).thenReturn(MOCK_HALL);

        Exposition exposition = MOCK_EXPOSITION;

        mockMvc.perform(post("/admin/add-exposition")
                .flashAttr("exposition", exposition)
                .param("hallId", MOCK_HALL.getId().toString()))
                .andExpect(view().name("redirect:/admin"));

        ArgumentCaptor<Exposition> expositionCaptor = ArgumentCaptor.forClass(Exposition.class);

        verify(expositionService).add(expositionCaptor.capture());

        assertThat(expositionCaptor.getValue(), is(exposition));
    }

    @Test
    public void addExpositionShouldNotAddExposition() throws Exception {
        Exposition exposition = Exposition.builder()
                .title("Some title")
                .build();

        mockMvc.perform(post("/admin/add-exposition")
                .flashAttr("exposition", exposition)
                .param("hallId", MOCK_HALL.getId().toString()))
                .andExpect(view().name("admin-add-exposition"));
    }

    @Test
    public void addExpositionShouldNotAddExpositionWithIncorrectDates() throws Exception {
        Exposition exposition = Exposition.builder()
                .id(MOCK_EXPOSITION.getId())
                .title(MOCK_EXPOSITION.getTitle())
                .theme(MOCK_EXPOSITION.getTheme())
                .startDate(MOCK_EXPOSITION.getStartDate())
                .endDate(LocalDate.of(2018, 11, 11))
                .ticketPrice(MOCK_EXPOSITION.getTicketPrice())
                .description(MOCK_EXPOSITION.getDescription())
                .build();

        final ModelAndView modelAndView = mockMvc.perform(post("/admin/add-exposition")
                .flashAttr("exposition", exposition)
                .param("hallId", MOCK_HALL.getId().toString()))
                .andExpect(view().name("admin-add-exposition"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        assertThat(model.get("dateError"), is(true));
    }

    @Test
    public void addExpositionShouldNotAddExpositionWithIncorrectHall() throws Exception {
        Exposition exposition = Exposition.builder()
                .id(MOCK_EXPOSITION.getId())
                .title(MOCK_EXPOSITION.getTitle())
                .theme(MOCK_EXPOSITION.getTheme())
                .startDate(MOCK_EXPOSITION.getStartDate())
                .endDate(MOCK_EXPOSITION.getEndDate())
                .ticketPrice(MOCK_EXPOSITION.getTicketPrice())
                .description(MOCK_EXPOSITION.getDescription())
                .build();

        when(hallService.showById(anyLong())).thenThrow(EntityNotFoundException.class);

        final ModelAndView modelAndView = mockMvc.perform(post("/admin/add-exposition")
                .flashAttr("exposition", exposition)
                .param("hallId", MOCK_HALL.getId().toString()))
                .andExpect(view().name("admin-add-exposition"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        assertThat(model.get("hallError"), is(true));
    }

    @Test
    public void addHallPage() throws Exception {
        final ModelAndView modelAndView = mockMvc.perform(get("/admin/add-hall"))
                .andExpect(view().name("admin-add-hall"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        assertThat(model.get("hall"), is(notNullValue()));
    }

    @Test
    public void addHallShouldAddHall() throws Exception {
        Hall hall = MOCK_HALL;

        mockMvc.perform(post("/admin/add-hall")
                .flashAttr("hall", hall))
                .andExpect(view().name("redirect:/admin"));

        ArgumentCaptor<Hall> hallCaptor = ArgumentCaptor.forClass(Hall.class);

        verify(hallService).add(hallCaptor.capture());

        assertThat(hallCaptor.getValue(), is(hall));
    }

    @Test
    public void addHallShouldNotAddHall() throws Exception {
        Hall hall = Hall.builder()
                .name("Some hall")
                .build();

        mockMvc.perform(post("/admin/add-hall")
                .flashAttr("hall", hall))
                .andExpect(view().name("admin-add-hall"));
    }

    @Test
    public void showUsersShouldShowUsers() throws Exception {
        when(userService.showAll(anyInt(), anyInt())).thenReturn(singletonList(MOCK_USER));
        when(userService.showEntriesAmount()).thenReturn(1L);

        final ModelAndView modelAndView = mockMvc.perform(get("/admin/users")
                .param("page", "1")
                .param("rowCount", "15"))
                .andExpect(view().name("admin-users"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        verify(userService).showAll(0, 15);
        verify(userService).showEntriesAmount();

        assertThat(model.get("users"), is(notNullValue()));
        assertThat(model.get("page"), is(1));
        assertThat(model.get("rowCount"), is(15));
        assertThat(model.get("numberOfPages"), is(1));
        assertThat(model.get("command"), is("/admin/users"));
    }

    @Test
    public void showHallsShouldReturnHalls() throws Exception {
        when(hallService.showAll()).thenReturn(singletonList(MOCK_HALL));

        final ModelAndView modelAndView = mockMvc.perform(get("/admin/halls"))
                .andExpect(view().name("admin-halls"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        verify(hallService).showAll();
        assertThat(model.get("halls"), is(notNullValue()));
    }

    @Test
    public void showExpositionsShouldReturnExpositions() throws Exception {
        when(expositionService.showAll(anyInt(), anyInt())).thenReturn(singletonList(MOCK_EXPOSITION));
        when(expositionService.showEntriesAmount()).thenReturn(1L);

        final ModelAndView modelAndView = mockMvc.perform(get("/admin/expositions")
                .param("page", "1")
                .param("rowCount", "15"))
                .andExpect(view().name("admin-expositions"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        verify(expositionService).showAll(0, 15);

        assertThat(model.get("expositions"), is(notNullValue()));
        assertThat(model.get("page"), is(1));
        assertThat(model.get("rowCount"), is(15));
        assertThat(model.get("numberOfPages"), is(1));
        assertThat(model.get("command"), is("/admin/expositions"));
    }

    @Test
    public void validatePaginationShouldThrowIllegalPaginationValuesException() throws Exception {
        mockMvc.perform(get("/admin/expositions")
                .param("page", "a")
                .param("rowCount", "s"))
                .andExpect(view().name("error"));
    }

    @Test
    public void handleExpositionAlreadyExistExceptionTest() throws Exception {
        doThrow(ExpositionAlreadyExistException.class).when(expositionService).add(any(Exposition.class));

        when(hallService.showById(anyLong())).thenReturn(MOCK_HALL);
        when(hallService.showAll()).thenReturn(singletonList(MOCK_HALL));

        final ModelAndView modelAndView = mockMvc.perform(post("/admin/add-exposition")
                .flashAttr("exposition", MOCK_EXPOSITION)
                .param("hallId", MOCK_HALL.getId().toString()))
                .andExpect(view().name("admin-add-exposition"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        assertThat(model.get("halls"), is(notNullValue()));
        assertThat(model.get("expositionError"), is(true));
        assertThat(model.get("exposition"), is(notNullValue()));
    }

}
