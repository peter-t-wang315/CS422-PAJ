package cpts422PAJ.personalPlanner.integrationTests;

import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.services.TaskService;
import cpts422PAJ.personalPlanner.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @Test
    public void testTaskAssignmentFlow() throws Exception {
        when(userService.findActiveUser()).thenReturn(1L);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.getUserById(1L)).thenReturn(new Users()); // Mock getUserById
        when(taskService.getTasksForUser(any(Users.class))).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        verify(userService).findActiveUser();
        verify(userService).checkIfAdmin(); // Verify this call
        verify(userService).getUserById(1L); // Verify getUserById
        verify(taskService).getTasksForUser(any(Users.class));
    }

}
