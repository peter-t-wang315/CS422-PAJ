package cpts422PAJ.personalPlanner.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.services.TaskService;
import cpts422PAJ.personalPlanner.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTaskServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @Test
    public void testAddTask() throws Exception {
        Task mockTask = new Task();
        when(taskService.save(any(Task.class))).thenReturn(mockTask);
        when(userService.findActiveUser()).thenReturn(1L); // Assuming an active user is present

        mockMvc.perform(get("/addTask"))
                .andExpect(status().isOk())
                .andExpect(view().name("addTask"));

        verify(taskService).save(any(Task.class));
    }
}