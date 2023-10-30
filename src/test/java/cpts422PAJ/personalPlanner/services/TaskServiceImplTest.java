package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Tag;
import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.repositories.TagRepository;
import cpts422PAJ.personalPlanner.repositories.TaskRepository;
import cpts422PAJ.personalPlanner.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private Task task;
    @Mock
    private Tag tag;
    private Users jon;
    private Tag none;
    private Date date;
    private Task jonHW1;
    private Task jonHW2;
    private Task jonHW3;
    private List<Task> taskList;
    @InjectMocks
    private TaskServiceImpl taskService;



    @BeforeEach
    void setUp() {
        jon = new Users("jon.barios.pore@wsu.edu", "jonb", "pass","John", "Pore", false);
        none = new Tag("None");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = null;
        try {
            date = dateFormat.parse(("14/11/2023"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        jonHW1 = new Task("Jon hw1", false, "do this", date, jon, none);
        jonHW2 = new Task("Jon nothing", false, "do this", date, jon, none);
        jonHW3 = new Task("Jon hw3", false, "do this", date, jon, none);
        taskList = List.of(
                jonHW1,
                jonHW2,
                jonHW3
        );

        when(taskRepository.findTasksByUserId(1L)).thenReturn(taskList);
    }

    @Test
    void findAll() {

    }

    @Test
    void getTasksForUser() {
    }

    @Test
    void getTasksByTag() {
    }

    @Test
    void getTaskById() {
    }

    @Test
    void save() {
    }

    @Test
    void uniquenessCalculatorTest() {
        assertEquals(0, taskService.uniquenessCalculator(5L));
        assertEquals(1, taskService.uniquenessCalculator(10L));
        assertEquals(2, taskService.uniquenessCalculator(100000L));
    }

    @Test
    void amountOfTasksTest() {
        assertEquals(3L, taskService.amountOfTasks(1L));
    }

    @Test
    void calculateNewDueDateTest() {
        when(task.getManuallySetDueDate()).thenReturn(date);
        assertEquals(date, taskService.calculateNewDueDate(jonHW1));

        // Missing the first if statement and then going into the second and third if statement
        Timestamp created = Timestamp.valueOf("2002-01-01 00:00:00");
        when(task.getManuallySetDueDate()).thenReturn(null);
        when(task.getCreated()).thenReturn(created);
        when(tag.getName()).thenReturn("Homework");
        Calendar expected = Calendar.getInstance();
        expected.setTime(created);
        expected.add(Calendar.DAY_OF_MONTH, 7);
        assertEquals(new Date(expected.getTimeInMillis()), taskService.calculateNewDueDate(jonHW1));






    }

    @Test
    void getDueDateIncrementTest() {
    }

    @Test
    void isAlliterationTest() {
    }

    @Test
    void characterCountOver8Test() {
    }
}