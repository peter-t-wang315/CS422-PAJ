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

        Users jon = new Users("jon.barios.pore@wsu.edu", "jonb", "pass","John", "Pore", false);
        Users jennifer = new Users("jennifer.doe@wsu.edu","jDoe", "123","Jennifer", "Doe", false);
        Users sergio = new Users("sergio.ramos@wsu.edu", "ser", "ser", "Sergio", "Ramos",false);
        Users admin = new Users("admin@gmail.com", "admin", "admin", "admin", "admin", true);
//        Users jack = new Users("jack.doe@wsu.edu","jackd", "yer","jack", "Doe");
        jon = userRepository.save(jon);
        jennifer = userRepository.save(jennifer);
        sergio = userRepository.save(sergio);
        admin = userRepository.save(admin);
//        jack = userRepository.save(jack);

        Tag none = new Tag("None");
        Tag homework = new Tag("Homework");
        Tag life = new Tag("Life");
        
        none = tagRepository.save(none);
        homework = tagRepository.save(homework);
        life = tagRepository.save(life);



        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(("14/11/2023"));
        Task hw1 = new Task("homework1", false, "do this", date, jon,homework);
        Task hw2 = new Task("homework2", false, "do this", date, jennifer,homework);
        Task hw3 = new Task("homework3", false, "do this", date, jon,life);
        Task sergioHW = new Task("Sergios hw1", false, "do this", date, sergio, homework);
        Task jonHW = new Task("Jons hw2", false, "do this", date, jon, homework);
        Task jennifersTasks = new Task("Jennifers hw1", false, "do this", date, jennifer, homework);

        hw1 = taskRepository.save(hw1);
        hw2 = taskRepository.save(hw2);
        hw3 = taskRepository.save(hw3);

        sergioHW = taskRepository.save(sergioHW);
        sergioHW = new Task("Sergios hw2", false, "do this", date, sergio, homework);
        sergioHW = taskRepository.save(sergioHW);
        sergioHW = new Task("Sergios hw3", false, "do this", date, sergio, homework);
        sergioHW = taskRepository.save(sergioHW);
        sergioHW = new Task("Sergios hw4", false, "do this", date, sergio, homework);
        sergioHW = taskRepository.save(sergioHW);
        sergioHW = new Task("Sergios hw5", false, "do this", date, sergio, homework);
        sergioHW = taskRepository.save(sergioHW);
        sergioHW = new Task("Sergios hw6", false, "do this", date, sergio, homework);
        sergioHW = taskRepository.save(sergioHW);
        sergioHW = new Task("Sergios hw7", false, "do this", date, sergio, homework);
        sergioHW = taskRepository.save(sergioHW);
        sergioHW = new Task("Sergios life stuff", false, "do this", date, sergio, life);
        sergioHW = taskRepository.save(sergioHW);
        sergioHW = new Task("Sergios hw9", false, "do this", date, sergio, homework);
        sergioHW = taskRepository.save(sergioHW);
        sergioHW = new Task("Sergios hw10", false, "do this", date, sergio, homework);

        jonHW = taskRepository.save(jonHW);
        jonHW = new Task("Jon hw4", false, "do this", date, jon, homework);
        jonHW = taskRepository.save(jonHW);
        jonHW = new Task("Jon nothing", false, "do this", date, jon, none);
        jonHW = taskRepository.save(jonHW);
        jonHW = new Task("Jon hw6", false, "do this", date, jon, homework);
        jonHW = taskRepository.save(jonHW);
        jonHW = new Task("Jon hw7", false, "do this", date, jon, homework);
        jonHW = taskRepository.save(jonHW);
        jonHW = new Task("Jon Life stuff", false, "do this", date, jon, life);
        jonHW = taskRepository.save(jonHW);
        jonHW = new Task("Jon Life stuff 2", false, "do this", date, jon, life);
        jonHW = taskRepository.save(jonHW);
        jonHW = new Task("Jon hw9", false, "do this", date, jon, homework);

        jennifersTasks = taskRepository.save(jennifersTasks);
        jennifersTasks = new Task("Jennifers hw2", false, "do this", date, jennifer, homework);
        jennifersTasks = taskRepository.save(jennifersTasks);
        jennifersTasks = new Task("Jennifers hw3", false, "do this", date, jennifer, homework);
        jennifersTasks = taskRepository.save(jennifersTasks);
        jennifersTasks = new Task("Jennifers nothing", false, "do this", date, jennifer, none);

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
