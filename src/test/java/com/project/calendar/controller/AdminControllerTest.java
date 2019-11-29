package com.project.calendar.controller;

import com.project.calendar.service.ExpositionService;
import com.project.calendar.service.HallService;
import com.project.calendar.service.UserService;
import com.project.calendar.service.util.ValidatePagination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
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
    private ValidatePagination validatePagination;

    @Test
    public void mainShouldReturnAdminPage() throws Exception {
        mockMvc.perform(get("/admin"));
    }

}