package com.project.calendar.controller;

import com.project.calendar.configuration.LoginSuccessHandler;
import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Payment;
import com.project.calendar.domain.Ticket;
import com.project.calendar.domain.User;
import com.project.calendar.service.ExpositionService;
import com.project.calendar.service.PaymentService;
import com.project.calendar.service.TicketService;
import com.project.calendar.service.UserService;
import com.project.calendar.service.helper.PDFCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@WithMockUser(username = "user@gmail.com", authorities = "USER")
public class UserControllerTest {
    private static final User USER = MOCK_USER;
    private static final Exposition EXPOSITION = MOCK_EXPOSITION;
    private static final Payment PAYMENT = MOCK_PAYMENT;
    private static final Ticket TICKET = MOCK_TICKET;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExpositionService expositionService;

    @MockBean
    private TicketService ticketService;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private PDFCreator pdfCreator;

    @MockBean
    private LoginSuccessHandler loginSuccessHandler;

    @MockBean
    private UserService userService;

    @Test
    public void viewExpositionShouldShowExpositions() throws Exception {
        when(expositionService.showAllNotFinished(anyInt(), anyInt())).thenReturn(singletonList(EXPOSITION));
        when(expositionService.showNotFinishedEntriesAmount()).thenReturn(1L);

        final ModelAndView modelAndView = mockMvc.perform(get("/user/expositions")
                .param("page", "1")
                .param("rowCount", "15"))
                .andExpect(view().name("user-view-expositions"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        verify(expositionService).showAllNotFinished(0, 15);
        verify(expositionService).showNotFinishedEntriesAmount();

        assertThat(model.get("expositions"), is(notNullValue()));
        assertThat(model.get("page"), is(1));
        assertThat(model.get("rowCount"), is(15));
        assertThat(model.get("numberOfPages"), is(1));
        assertThat(model.get("command"), is("/user/expositions"));
    }

    @Test
    public void chooseDateShouldShowChooseDatePage() throws Exception {
        when(expositionService.showById(anyLong())).thenReturn(EXPOSITION);

        final ModelAndView modelAndView = mockMvc.perform(post("/user/choose-date")
                .param("expositionId", "1"))
                .andExpect(view().name("user-choose-date"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        assertThat(model.get("startDate"), not(EXPOSITION.getStartDate()));
    }

    @Test
    public void chooseDateShouldShowChooseDatePageWithExpositionStartDate() throws Exception {
        Exposition exposition = Exposition.builder()
                .id(EXPOSITION.getId())
                .title(EXPOSITION.getTitle())
                .theme(EXPOSITION.getTheme())
                .startDate(LocalDate.now().plusDays(2))
                .endDate(EXPOSITION.getEndDate())
                .ticketPrice(EXPOSITION.getTicketPrice())
                .description(EXPOSITION.getDescription())
                .hall(EXPOSITION.getHall())
                .build();

        when(expositionService.showById(anyLong())).thenReturn(exposition);

        final ModelAndView modelAndView = mockMvc.perform(post("/user/choose-date")
                .param("expositionId", "1"))
                .andExpect(view().name("user-choose-date"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        assertThat(model.get("startDate"), is(exposition.getStartDate()));
    }

    @Test
    public void checkoutShouldReturnCheckoutPage() throws Exception {

        final ModelAndView modelAndView = mockMvc.perform(post("/user/checkout")
                .sessionAttr("exposition", EXPOSITION)
                .param("date", "2019-12-05")
                .param("tickets", "3"))
                .andExpect(view().name("user-checkout-page"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        assertThat(model.get("ticketAmount"), is(3));
        assertThat(model.get("ticketPrice"), is(EXPOSITION.getTicketPrice().doubleValue()));
    }

    @Test
    public void processPaymentShouldProcessPayment() throws Exception {
        when(paymentService.showLastByUserId(any())).thenReturn(PAYMENT);

        mockMvc.perform(post("/user/payment-process")
                .sessionAttr("user", USER)
                .sessionAttr("exposition", EXPOSITION)
                .sessionAttr("date", LocalDate.of(2019, 12, 5))
                .sessionAttr("ticketAmount", PAYMENT.getTicketsAmount())
        ).andExpect(view().name("redirect:/user/tickets"));

        verify(paymentService).add(any(Payment.class));

        verify(ticketService, times(PAYMENT.getTicketsAmount())).add(any(Ticket.class));
    }

    @Test
    public void showTicketsShouldShowTickets() throws Exception {
        when(paymentService.showAllByUserId(anyLong())).thenReturn(singletonList(PAYMENT));
        when(ticketService.showAllByUserId(anyLong())).thenReturn(singletonList(TICKET));

        final ModelAndView modelAndView = mockMvc.perform(get("/user/tickets")
                .sessionAttr("user", USER))
                .andExpect(view().name("user-show-tickets"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        assertThat(model.get("tickets"), is(notNullValue()));
        assertThat(model.get("ticketsAmount"), is(notNullValue()));
    }

    @Test
    public void downloadShouldReturnErrorPage() throws Exception {
        when(ticketService.showAllByPaymentIdAndUserId(anyLong(), anyLong())).thenReturn(singletonList(TICKET));
        when(pdfCreator.createPDF(anyLong(), anyList())).thenReturn("some");

        mockMvc.perform(get("/user/download")
                .sessionAttr("user", USER)
                .param("paymentId", "2"))
                .andExpect(view().name("error"));
    }

}
