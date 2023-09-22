package cpts422PAJ.personalPlanner.setup;

import cpts422PAJ.personalPlanner.entities.User;
import cpts422PAJ.personalPlanner.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialSetup implements CommandLineRunner {

    private UserRepository userRepository;

    public InitialSetup(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User jon = new User("jon.b@wsu.edu", "jonb","Jon", "b");
        User jane = new User("jane.doe@wsu.edu", "jDoe","jane", "Doe");

    }
}
