package cpts422PAJ.personalPlanner.controllers;

import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.services.TaskService;
import cpts422PAJ.personalPlanner.services.UserService;
import cpts422PAJ.personalPlanner.services.UserServiceImpl;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.apache.catalina.User;
import org.apache.juli.logging.Log;
import org.hibernate.Internal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static java.lang.reflect.Array.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginController controller;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getUsers ()throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/login")).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login.html"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("userNames"));
    }

    @Test
    void getUsername() throws Exception {

        when(userService.isUser("testUser", "testUserPass")).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/loginInfo")
                        .param("userName", "testUser")
                        .param("passWord", "testUserPass"))
                .andExpect(MockMvcResultMatchers.view().name("login"));

        verify(userService, times(1)).isUser("testUser", "testUserPass");
        verify(userService, never()).changeUserActivity("testUser","testUserPass");
    }

    @Test
    void getUsername2() throws Exception {


        when(userService.isUser("testUser", "testUserPass")).thenReturn(true);

        doNothing().when(userService).changeUserActivity("validUser", "testUserPass");

        mockMvc.perform(MockMvcRequestBuilders.post("/loginInfo")
                        .param("userName", "testUser")
                        .param("passWord", "testUserPass"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));


        verify(userService, times(1)).isUser("testUser", "testUserPass");
        verify(userService, times(1)).changeUserActivity("testUser", "testUserPass");

    }

    @Test
    void logOut() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/logOut"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"));



    }

    @Test
    void register()  throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register.html"));

    }

    @Test
    void registerUser()  throws Exception { //should just go into register

        when(userService.checkSameUser("testUser")).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/registerUser")
                        .param("userName", "testUser")
                        .param("passWord", "testUserPass")
                        .param("email", "testUserEmail")
                        .param("first", "testUserF")
                        .param("last", "testUserL")
                        .param("adminPassword", "testUserAdPass"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));


    }

    @Test
    void registerUser2()  throws Exception { //should just go into register

        when(userService.checkSameUser("testUser")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/registerUser")
                        .param("userName", "testUser")
                        .param("passWord", "testUserPass")
                        .param("email", "testUserEmail")
                        .param("first", "testUserF")
                        .param("last", "testUserL")
                        .param("adminPassword", "testUserAdPass"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));


    }

    @Test
    void registerUser3()  throws Exception { //should just go into admin

        when(userService.checkSameUser("testUser")).thenReturn(true);
        when(userService.getAdminPassword()).thenReturn("testUserAdPass");
        mockMvc.perform(MockMvcRequestBuilders.post("/registerUser")
                        .param("userName", "testUser")
                        .param("passWord", "testUserPass")
                        .param("email", "testUserEmail")
                        .param("first", "testUserF")
                        .param("last", "testUserL")
                        .param("adminPassword", "testUserAdPass"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));


    }

    @Test
    void registerUser4()  throws Exception { //should just go into admin

        when(userService.checkSameUser("testUser")).thenReturn(true);
        when(userService.getAdminPassword()).thenReturn("testUserAdPass");
        when(userService.checkEmail("testUserEmail@wsu.edu")).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.post("/registerUser")
                        .param("userName", "testUser")
                        .param("passWord", "testUserPass")
                        .param("email", "testUserEmail@wsu.edu")
                        .param("first", "testUserF")
                        .param("last", "testUserL")
                        .param("adminPassword", "testUserAdPass"))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));


    }

}