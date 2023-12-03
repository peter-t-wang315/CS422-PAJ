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
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private Task taskMock;
    @Mock
    private Tag tagMock;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private TagServiceImpl tagService;
    @Mock
    private Users users;
    private Users jon;
    private Tag none;
    private Tag homework;
    private Date date;
    private Task jonHW1;
    private Task jonHW2;
    private Task jonHW3;
    private List<Task> taskList;
    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        jon = new Users("jon.barios.pore@wsu.edu", "jonb", "pass","John", "Pore", false);
        none = new Tag("None");
        homework = new Tag("Homework");
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
    void saveTestAdmin() {
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(date);
        when(userService.checkIfAdmin()).thenReturn(true);
        taskService.save(taskMock);
        verify(taskRepository).save(taskMock);
    }

    @Test
    void saveTestLongName() {
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(0L);
        when(taskMock.getTaskName()).thenReturn("oaisjfoiwjefioajiwofeiowfaefawef");
        taskService.save(taskMock);
        verify(taskRepository, times(0)).save(taskMock);
    }

    @Test
    void saveTestAlliterationUnique() {
        // Acceptable task as it is alliteration but the user is unique
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(10L);
        when(taskMock.getTaskName()).thenReturn("this this this this");
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        taskService.save(taskMock);
        verify(taskRepository).save(taskMock);
    }

    @Test
    void saveTestAlliterationNotUnique() {
        // Not acceptable task as it is alliteration and user isn't unique
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(5L);
        when(taskMock.getTaskName()).thenReturn("this this this this");
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        taskService.save(taskMock);
        verify(taskRepository, times(0)).save(taskMock);
    }

    @Test
    void saveTestTagNameUnique() {
        // Acceptable task as user is unique enough to name tasks same as tags
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(-1L);
        when(taskMock.getTaskName()).thenReturn("None");
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        taskService.save(taskMock);
        verify(taskRepository).save(taskMock);
    }

    @Test
    void saveTestTagNameNotUnique() {
        // Not acceptable task as user is unique enough to name tasks same as tags
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(10L);
        when(taskMock.getTaskName()).thenReturn("None");
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        taskService.save(taskMock);
        verify(taskRepository, times(0)).save(taskMock);
    }

    @Test
    void saveTestCharacterCountMediumUniqueZ() {
        // This will be the acceptable route
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(10L);
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        when(taskMock.getTaskName()).thenReturn("asaaaaaaaaaaza");
        taskService.save(taskMock);
        verify(taskRepository).save(taskMock);
    }

    @Test
    void saveTestCharacterCountMediumUniqueW() {
        // This will be the acceptable route
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(10L);
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        when(taskMock.getTaskName()).thenReturn("asaaaaaaaaaawa");
        taskService.save(taskMock);
        verify(taskRepository).save(taskMock);
    }

    @Test
    void saveTestCharacterCountMediumUniqueX() {
        // This will be the acceptable route
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(10L);
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        when(taskMock.getTaskName()).thenReturn("asaaaaaaaaaaxa");
        taskService.save(taskMock);
        verify(taskRepository).save(taskMock);
    }

    @Test
    void saveTestCharacterCountMediumUnique2() {
        // This will be the unacceptable route
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(10L);
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        when(taskMock.getTaskName()).thenReturn("asaaaaaaaaaaa");
        taskService.save(taskMock);
        verify(taskRepository,times(0)).save(taskMock);
    }

    @Test
    void saveTestCharacterCountMaxUnique() {
        // This is acceptable as user is max unique
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(-1L);
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        when(taskMock.getTaskName()).thenReturn("asaaaaaaaaaaa");
        taskService.save(taskMock);
        verify(taskRepository).save(taskMock);
    }

    @Test
    void saveTestCharacterCountNotUnique() {
        // This is unacceptable as user is not unique
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(5L);
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        when(taskMock.getTaskName()).thenReturn("asaaaaaaaaaaa");
        taskService.save(taskMock);
        verify(taskRepository, times(0)).save(taskMock);
    }

    @Test
    void saveTestDueDateUnique() {
        // This is acceptable as user is max unique
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(-1L);
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        when(taskMock.getTaskName()).thenReturn("due date");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -100);
        // Get the new due date as a Date object
        Date newDueDate = calendar.getTime();
        when(taskMock.getDueDate()).thenReturn(newDueDate);
        taskService.save(taskMock);
        verify(taskRepository).save(taskMock);
    }

    @Test
    void saveTestDueDateNotUnique() {
        // This is acceptable as user is max unique
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(5L);
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        when(taskMock.getTaskName()).thenReturn("due date");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -100);
        // Get the new due date as a Date object
        Date newDueDate = calendar.getTime();
        when(taskMock.getDueDate()).thenReturn(newDueDate);
        taskService.save(taskMock);
        verify(taskRepository, times(0)).save(taskMock);
    }

    @Test
    void saveTestDueDateNull() {
        // This is acceptable as there is no due date
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(5L);
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        when(taskMock.getTaskName()).thenReturn("due date");
        when(taskMock.getDueDate()).thenReturn(null);
        taskService.save(taskMock);
        verify(taskRepository).save(taskMock);
    }

    @Test
    void saveTestDueDateAfterCurrentTime() {
        // This is acceptable as user is max unique
        when(taskService.calculateNewDueDate(taskMock)).thenReturn(null);
        when(userService.checkIfAdmin()).thenReturn(false);
        when(userService.notUnique()).thenReturn(5L);
        when(tagService.findAll()).thenReturn(List.of(none, homework));
        when(taskMock.getTaskName()).thenReturn("due date");
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Date currentDate = new Date(currentTime.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 100);
        // Get the new due date as a Date object
        Date newDueDate = calendar.getTime();
        when(taskMock.getDueDate()).thenReturn(newDueDate);
        taskService.save(taskMock);
        verify(taskRepository).save(taskMock);
    }


    @Test
    void findAllTest() {
        taskService.findAll();
        verify(taskRepository).findAll();
    }

    @Test
    void getTasksForUserTest() {
        when(users.getId()).thenReturn(1L);
        taskService.getTasksForUser(users);
        verify(taskRepository).findTasksByUserId(users.getId());
    }

    @Test
    void getTaksByTagTest() {
        when(tagMock.getId()).thenReturn(1L);
        taskService.getTasksByTag(tagMock);
        verify(taskRepository).findTasksByTagId(tagMock.getId());
    }

    @Test
    void getTaskByIdTest() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(jonHW1));
        taskService.getTaskById(1L);
        verify(taskRepository).findById(1L);
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
        reset(taskMock);
        when(taskMock.getManuallySetDueDate()).thenReturn(date);
        assertEquals(date, taskService.calculateNewDueDate(taskMock));

        // Missing the first if statement and then going into the second and third if statement
        Timestamp created = Timestamp.valueOf("2002-01-01 00:00:00");
        when(taskMock.getManuallySetDueDate()).thenReturn(null);
        when(taskMock.getCreated()).thenReturn(created);
        when(taskMock.getTag()).thenReturn(tagMock);
        when(tagMock.getName()).thenReturn("Homework");
        Calendar expected = Calendar.getInstance();
        expected.setTime(created);
        expected.add(Calendar.DAY_OF_MONTH, 7);
        assertEquals(new Date(expected.getTimeInMillis()), taskService.calculateNewDueDate(taskMock));

        // Nif, if, else
        when(taskMock.getManuallySetDueDate()).thenReturn(null);
        when(taskMock.getTag()).thenReturn(tagMock);
        when(tagMock.getName()).thenReturn("None");
        assertNull(taskService.calculateNewDueDate(taskMock));

        // Nif, else
        when(taskMock.getTag()).thenReturn(null);
        when(taskMock.getDueDate()).thenReturn(date);
        assertEquals(date, taskService.calculateNewDueDate(taskMock));
    }

    @Test
    void getDueDateIncrementTest() {
        assertEquals(7, taskService.getDueDateIncrement("Homework"));
        assertEquals(14, taskService.getDueDateIncrement("Work"));
        assertEquals(3, taskService.getDueDateIncrement("Life"));
        assertEquals(0, taskService.getDueDateIncrement("None"));
        assertEquals(0, taskService.getDueDateIncrement("Unknown"));
        assertEquals(0, taskService.getDueDateIncrement("homework"));
    }

    @Test
    void isAlliterationTest() {
        // Don't enter for loop
        assertFalse(taskService.isAlliteration(""));
        // Enter for loop
        // if, if, if
        assertTrue(taskService.isAlliteration("sad siwjf oijds soiwje soiw"));

        assertFalse(taskService.isAlliteration("123 asf soifj"));
    }

    @Test
    void characterCountOver8Test() {
        // if
        assertTrue(taskService.characterCountOver8(""));

        assertTrue(taskService.characterCountOver8(null));

        // Nif if
        assertTrue(taskService.characterCountOver8("jijiiiiiiiiiiiii"));

        // Nif else
        assertFalse(taskService.characterCountOver8("jkh"));

        assertFalse(taskService.characterCountOver8("1o!/23213oj:`{}#$%*^%&$#@!23"));
    }
}