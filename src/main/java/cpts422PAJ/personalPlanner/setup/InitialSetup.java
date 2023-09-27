package cpts422PAJ.personalPlanner.setup;

import cpts422PAJ.personalPlanner.repositories.TaskRepository;
import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.repositories.UserRepository;
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
        System.out.println("Starting DataBase....");

        Users jon = new Users("jon.b@wsu.edu", "jonb", "pass","Jon", "b");
        Users jane = new Users("jane.doe@wsu.edu","jDoe", "123","jane", "Doe");
        jon = userRepository.save(jon);
        jane = userRepository.save(jane);


        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(("25/09/2023"));
        Task hw1 = new Task("homework1", false, "do this", date, jon);
        Task hw2 = new Task("homework2", false, "do this", date,jane);
        Task hw3 = new Task("homework3", false, "do this", date,jon);

        hw1 = taskRepository.save(hw1);
        hw2 = taskRepository.save(hw2);
        hw3 = taskRepository.save(hw3);




        System.out.println(hw1);
        System.out.println(hw2);
        System.out.println(hw3);



    }
}
