package cpts422PAJ.personalPlanner.setup;

import cpts422PAJ.personalPlanner.Repositories.TaskRepository;
import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class InitialSetup implements CommandLineRunner {

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    public InitialSetup(UserRepository userRepository, TaskRepository taskRepository) {

        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("hello");


//        Users jon = new Users("jon.b@wsu.edu", "jonb","Jon", "b");
//        Users jane = new Users("jane.doe@wsu.edu", "jDoe","jane", "Doe");
//        jon = userRepository.save(jon);
//        jane = userRepository.save(jane);
//
//
//        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        Date date = dateFormat.parse(("09/25/2023"));
//
//        //String taskName, boolean completed, String note, Timestamp dueDate
//        Task hw1 = new Task("homework1", false, "do this", date);
//        Task hw2 = new Task("homework2", false, "do this", date);
//        jon.addTask((hw1));
//        jane.addTask((hw2));
//
//        hw1 = taskRepository.save(hw1);
//        hw2 = taskRepository.save(hw2);
//        System.out.println("Starting database...");
//
//        System.out.println(jon);


    }
}
