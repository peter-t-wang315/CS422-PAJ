package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Tag;
import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.repositories.TaskRepository;
import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private UserService userService;

    private TagService tagService;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, TagService tagService) {
        this.userService = userService;
        this.taskRepository = taskRepository;
        this.tagService = tagService;
    }

    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getTasksForUser(Users user) { return taskRepository.findTasksByUserId(user.getId());}
    @Override
    public List<Task> getTasksByTag(Tag tag) { return taskRepository.findTasksByTagId(tag.getId()); }
    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public Task save(Task task) {
        Date newDueDate = calculateNewDueDate(task);
        if (newDueDate == null) {
            System.out.println("There is no current due date");
            task.setDueDate(null);
        }
        else {
            task.setDueDate(new Timestamp(newDueDate.getTime()));
        }
        // Admins have free range
        if (userService.checkIfAdmin()) {
            return taskRepository.save(task);
        }

        Long uniqueness = userService.notUnique();
        int uniquenessLevel = uniquenessCalculator(uniqueness);
        String taskName = task.getTaskName().toLowerCase();
        // If the task name is longer than 30 characters it isn't allowed
        if (taskName.length() > 30) {
            System.out.println("This task name is too long");
            return task;
        }
        // Can't save tasks that use alliteration. This is because all of the words start with
        // the same letter which isn't unique
        // Nested if: If the user is ANY unique it doesn't matter
        else if (isAlliteration(taskName)) {
            if (uniquenessLevel > 0) {
                System.out.println("Is alliteration but user is unique");
            }
            else {
                System.out.println("This task is using alliteration which is not unique");
                return task;
            }
        }
        // Can't save tasks if they are the same name as a tag
        // Nested if: If the user is MAX unique it doesn't matter
        int tagNameFlag = 0;
        for (Tag tag : tagService.findAll()) {
            if (tag.getName().toLowerCase().equals(taskName)) {
                if (uniquenessLevel == 2) {
                    System.out.println("User is max unique so this is fine");
                }
                else {
                    System.out.println("CANNOT SAVE: Task has the same name as a tag");
                    tagNameFlag = 1;
                }
            }
        }
        if (tagNameFlag == 0) {
            System.out.println("Task is not tag name, can be saved");
        }
        else {
            return task;
        }
        // If a task uses any letter more than 8 times, it can't be saved
        // Nested if: If the user is ANY unique but it has the letter's W, X, Z in it, it doesn't matter
        // Nested if: If the user is MAX unique it doesn't matter
        if (characterCountOver8(taskName)) {
            if (uniquenessLevel == 1) {
                if (taskName.contains(String.valueOf('w')) || taskName.contains(String.valueOf('x')) || taskName.contains(String.valueOf('z'))) {
                    System.out.println("Task has too many of the same letter but contains a W, X, or Z");
                }
                else {
                    System.out.println("CANNOT SAVE: Task has too many of the same letters without a w,x, or z");
                    return task;
                }
            }
            else if (uniquenessLevel == 2) {
                System.out.println("Task has too many of the same letter but is unique");
            }
            else {
                System.out.println("CANNOT SAVE: Task has too many of the same letter");
                return task;
            }
        }
        // If a task is made past due date, it can't be saved
        // Nested if: If the user is MAX uniqueness it doesn't matter
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (task.getDueDate() != null && currentTime.after(task.getDueDate()))
        {
            if (uniquenessLevel == 2) {
                System.out.println("Task overdue but user is unique");
            }
            else {
                System.out.println("CANNOT SAVE: Task it is after due date");
                return task;
            }
        }
        System.out.println("They did not violate any rules");
        task = taskRepository.save(task);
        return task;
    }

    public int uniquenessCalculator(Long uniqueness) {
        if (uniqueness == 5) {
            // LOW
            return 0;
        }
        // Past this line is ANY uniqueness
        else if (uniqueness == 10){
            // MID
            return 1;
        }
        else {
            // MAX
            return 2;
        }
    }

    public int amountOfTasks(Long uId){
        List<Task> currTasks = new ArrayList<>();
        currTasks = taskRepository.findTasksByUserId(uId);
        return currTasks.size();
    }

    public Date calculateNewDueDate(Task task) {
        Tag tag = task.getTag();

        // If the user creates a due date, this should trump everything else
        if (task.getManuallySetDueDate() != null) {
            return task.getManuallySetDueDate();
        }
        // If the user doesn't create a due date, add auto generated due dates based on tags
        if (tag != null) {
            int dueDateIncrement = getDueDateIncrement(tag.getName());
            // If the task has any tag that isn't the None tag, we must give it the due date increment
            if (dueDateIncrement != 0) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(task.getCreated());
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);
                cal.add(Calendar.DAY_OF_MONTH, dueDateIncrement);
                return new Date(cal.getTimeInMillis());
            }
            // If the task has the None tag, kill the due date
            else {
                System.out.println("The task doesn't have a due date meaning that it will be auto assigned one");
                return null;

            }
        }
        return task.getDueDate();
    }

    public int getDueDateIncrement(String tagName) {
        if (tagName.equals("Homework")) {
            return 7;
        }
        else if (tagName.equals("Work")) {
            return 14;
        }
        else if (tagName.equals("Life")) {
            return 3;
        }
        else if (tagName.equals("None")) {
            return 0;
        }
        return 0;
    }


    public boolean isAlliteration(String input) {
        // Create a character array to store counts of each letter
        int[] letterCounts = new int[26];

        // Split the input string into words
        String[] words = input.split("\\s+");

        for (String word : words) {
            if (!word.isEmpty()) {
                char firstLetter = Character.toLowerCase(word.charAt(0));
                if (Character.isLetter(firstLetter)) {
                    int index = firstLetter - 'a';
                    letterCounts[index]++;
                    if (letterCounts[index] > 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean characterCountOver8(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }
        int[] charCount = new int[26];
        input = input.toLowerCase();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c >= 'a' && c <= 'z') {
                int index = c - 'a';
                charCount[index]++;
            }
        }
        int maxCount = 0;
        for (int count : charCount) {
            if (count > maxCount) {
                maxCount = count;
            }
        }
        
        if (maxCount > 8) {
            return true;
        }
        else {
            return false;
        }
    }

}
