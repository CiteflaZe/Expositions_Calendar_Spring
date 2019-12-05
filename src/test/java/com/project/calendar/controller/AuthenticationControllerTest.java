package com.project.calendar.controller;

import com.project.calendar.configuration.LoginSuccessHandler;
import com.project.calendar.domain.User;
import com.project.calendar.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Objects;

import static com.project.calendar.MockData.MOCK_USER;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private LoginSuccessHandler loginSuccessHandler;

    @Test
    public void mainShouldReturnHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(view().name("index"));
    }

    @Test
    public void loginShouldReturnLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"));
    }

    @Test
    public void registerShouldReturnRegisterPage() throws Exception {
        final ModelAndView modelAndView = mockMvc.perform(get("/register"))
                .andExpect(view().name("register"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        assertThat(model.get("user"), is(notNullValue()));
    }

    @Test
    public void registerShouldRegisterUser() throws Exception {
        User user = MOCK_USER;

        mockMvc.perform(post("/register")
                .flashAttr("user", user)
                .param("passwordConfirmation", user.getPassword()))
                .andExpect(view().name("redirect:/"));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        verify(userService).register(userCaptor.capture());

        assertThat(userCaptor.getValue(), is(user));
    }

    @Test
    public void registerShouldNotRegisterUser() throws Exception {
        User user = User.builder()
                .name("Name")
                .build();

        mockMvc.perform(post("/register")
                .flashAttr("user", user)
                .param("passwordConfirmation", MOCK_USER.getPassword()))
                .andExpect(view().name("register"));
    }

    @Test
    public void registerShouldNotRegisterUserWithInvalidPasswordConfirmation() throws Exception {
        final ModelAndView modelAndView = mockMvc.perform(post("/register")
                .flashAttr("user", MOCK_USER)
                .param("passwordConfirmation", "pass"))
                .andExpect(view().name("register"))
                .andReturn().getModelAndView();

        final Map<String, Object> model = Objects.requireNonNull(modelAndView).getModel();

        assertThat(model.get("confirmationError"), is(true));
    }

}