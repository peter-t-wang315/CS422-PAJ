package cpts422PAJ.personalPlanner.entities;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        assertEquals("New Task Name", testTask.getTaskName());
    }

    @Test
    void isCompletedTest() {
        assertFalse(testTask.isCompleted());
    }


}