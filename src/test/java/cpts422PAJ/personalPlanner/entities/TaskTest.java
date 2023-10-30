package cpts422PAJ.personalPlanner.entities;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task testTask;
    private Users jon;
    private Tag none;
    private Tag homework;
    private Date date;

    @BeforeEach()
    void setUp() {
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
        testTask = new Task("Jon hw1", false, "do this", date, jon, none);
        testTask.setId(1L);
        Task emptyTask = new Task();
    }

    @Test
    void setIdTest() {
        testTask.setId(2L);
        assertEquals(2L, testTask.getId());
    }

    @Test
    void getIdTest() {
        assertEquals(1L,testTask.getId());
    }

    @Test
    void getTaskNameTest() {
        assertEquals("Jon hw1", testTask.getTaskName());
    }

    @Test
    void setTaskNameTest() {
        testTask.setTaskName("New task name");
        assertEquals("New task name", testTask.getTaskName());
    }

    @Test
    void isCompletedTest() {
        assertFalse(testTask.isCompleted());
    }

    @Test
    void UserAndTagAccessorsTest() {

        Users user = new Users();
        Tag tag = new Tag("Test Tag");
        Task task = new Task();

        task.setUser(user);
        task.setTag(tag);

        assertThat(task.getUser()).isEqualTo(user);
        assertThat(task.getTag()).isEqualTo(tag);
    }

    @Test
    void testToString() {

        Task task = new Task();
        task.setTaskName("Test Task");

        String result = task.toString();

        assertThat(result).contains("Test Task");
    }

    @Test
    void testTaskCreationAndFieldAccessors() {

        String taskName = "Test Task";
        boolean completed = false;
        String note = "This is a test task";
        Timestamp created = new Timestamp(new Date().getTime());
        Date dueDate = new Date();
        Users user = new Users(); // Assuming you have a constructor or a way to create a user
        Tag tag = new Tag("Test Tag");

        Task task = new Task(taskName, completed, note, dueDate, user, tag);
        task.setCreated(created);

        assertThat(task.getTaskName()).isEqualTo(taskName);
        assertThat(task.isCompleted()).isEqualTo(completed);
        assertThat(task.getNote()).isEqualTo(note);
        assertThat(task.getCreated()).isEqualTo(created);
        assertThat(task.getDueDate()).isEqualTo(dueDate);
        assertThat(task.getUser()).isEqualTo(user);
        assertThat(task.getTag()).isEqualTo(tag);
    }

    @Test
    void getManuallySetDueDateShouldReturnCorrectDate() {

        Task task = new Task();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date expectedDate = null;
        try {
            expectedDate = dateFormat.parse("15/11/2023");
        } catch (ParseException e) {
            fail("Failed to parse date");
        }

        task.setManuallySetDueDate(expectedDate);
        Date actualDate = task.getManuallySetDueDate();

        assertEquals(expectedDate, actualDate, "The manually set due date should be the same as the expected date");
    }

    @Test
    void setManuallySetDueDateShouldUpdateDueDate() {

        Task task = new Task();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date expectedDate = null;
        try {
            expectedDate = dateFormat.parse("15/11/2023");
        } catch (ParseException e) {
            fail("Failed to parse date");
        }

        task.setManuallySetDueDate(expectedDate);

        assertEquals(expectedDate, task.getManuallySetDueDate(), "The manually set due date should be updated");
    }

    @Test
    void getTagNameShouldReturnCorrectTagName() {

        Task task = new Task();
        Tag tag = new Tag("Homework");
        task.setTag(tag);

        String tagName = task.getTagName();

        assertEquals("Homework", tagName, "The tag name should be 'Homework'");
    }

    @Test
    void setCompletedShouldUpdateCompletedStatus() {

        Task task = new Task();
        assertFalse(task.isCompleted(), "Initially, task should not be completed");

        task.setCompleted(true);

        assertTrue(task.isCompleted(), "Task should be marked as completed");
    }

    @Test
    void setNoteShouldUpdateNote() {

        Task task = new Task();
        assertEquals("", task.getNote(), "Initially, task note should be an empty string");

        task.setNote("New Note");
        assertEquals("New Note", task.getNote(), "Task note should be updated");
    }

    @Test
    void setDueDateShouldUpdateDueDate() {

        Task task = new Task();
        assertNull(task.getDueDate(), "Initially, task due date should be null");
        Timestamp newDueDate = new Timestamp(System.currentTimeMillis());

        task.setDueDate(newDueDate);

        assertEquals(newDueDate, task.getDueDate(), "Task due date should be updated");
    }

    @Test
    void getUserIDShouldReturnCorrectUserID() {

        Long expectedUserId = 1L;
        Users user = new Users("email@example.com", "username", "password", "First", "Last", false) {
            public Long getId() {
                return expectedUserId;
            }
        };
        Task task = new Task();
        task.setUser(user);

        Long userID = task.getUserID();

        assertEquals(expectedUserId, userID, "getUserID should return the correct user ID");
    }
}