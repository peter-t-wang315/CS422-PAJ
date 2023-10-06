package cpts422PAJ.personalPlanner.setup;

import cpts422PAJ.personalPlanner.entities.Tag;
import cpts422PAJ.personalPlanner.repositories.TagRepository;
import cpts422PAJ.personalPlanner.repositories.TaskRepository;
import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitialSetup implements CommandLineRunner {

    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private TagRepository tagRepository;
    public InitialSetup(UserRepository userRepository, TaskRepository taskRepository, TagRepository tagRepository) {

        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting DataBase....");

        Users jon = new Users("jon.b@wsu.edu", "jonb", "pass","John", "Pore");
        Users jane = new Users("jane.doe@wsu.edu","jDoe", "123","jane", "Doe");
//        Users jack = new Users("jack.doe@wsu.edu","jackd", "yer","jack", "Doe");
        jon = userRepository.save(jon);
        jane = userRepository.save(jane);
//        jack = userRepository.save(jack);

        Tag homework = new Tag("Homework");
        Tag life = new Tag("Life");
        Tag none = new Tag("None");

        homework = tagRepository.save(homework);
        life = tagRepository.save(life);
        none = tagRepository.save(none);



        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(("25/09/2023"));
        Task hw1 = new Task("homework1", false, "do this", date, jon,homework);
        Task hw2 = new Task("homework2", false, "do this", date,jane,homework);
        Task hw3 = new Task("homework3", false, "do this", date,jon,life);

        hw1 = taskRepository.save(hw1);
        hw2 = taskRepository.save(hw2);
        hw3 = taskRepository.save(hw3);




        System.out.println(hw1);
        System.out.println(hw2);
        System.out.println(hw3);

        String  lastName = "Panos";
        char firstLetter = lastName.charAt(0);

        Set<Character> letterSet = new HashSet<Character>();

        letterSet.addAll(Arrays.asList(new Character[]{
                'M', 'N', 'O', 'P', 'Q','R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z'

        }));


//        letterSet.contains(firstLetter);

        System.out.println(letterSet.contains(firstLetter));

    }
}
