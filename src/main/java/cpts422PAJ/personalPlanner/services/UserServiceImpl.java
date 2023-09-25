package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Users save(Users users) {
        return null;
    }

    public boolean isUser(String name, String pass){
        List<String> names = new ArrayList<>();
        List<String> passwords = new ArrayList<>();
        userRepository.findAll().forEach(users -> names.add(users.getUserName()) );
        userRepository.findAll().forEach(users -> passwords.add(users.getPassword()));

        for (int i = 0; i< names.size();i++){
            if (Objects.equals(names.get(i), name)){
                if (Objects.equals(passwords.get(i), pass)){
                    return true;
                }
            }
        }

        return false;

    }

    public List getNames(){
        List names = new ArrayList<>();
        userRepository.findAll().forEach(users -> names.add(users.getUserName()) );
        return names;
    }

}
