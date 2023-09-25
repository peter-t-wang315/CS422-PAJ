package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.repositories.UserRepository;
import org.apache.catalina.User;
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
    public Users getUserById(Long id) {
        return userRepository.findById(id).get();
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

    public void changeUserActivity(String name, String pass){
        List<String> names = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        userRepository.findAll().forEach(users -> names.add(users.getUserName()) );
        userRepository.findAll().forEach(users -> ids.add(users.getId()) );
        for (int i = 0; i< names.size();i++){
            if (Objects.equals(names.get(i), name)){
                Users user = userRepository.findById(new Long(i+1)).get();
//                userRepository.findById(new Long(i+1)).get().setCurrUser(true);
                user.setCurrUser(true);
                userRepository.save(user);
                break;
            }
        }
    }

    public Long findActiveUser(){
        List<Boolean> currentUserList = new ArrayList<>();
        userRepository.findAll().forEach(users -> currentUserList.add(users.isCurrentUser()));
//        System.out.print("In findActiveUser");
        for(int i =0; i < currentUserList.size(); i++){
            if(currentUserList.get(i)){

                return new Long(i+1);
            }
        }

        return new Long(0);
    }

    public void logOffUser(){
        List<Boolean> currentUsers = new ArrayList<>();
        userRepository.findAll().forEach(users -> currentUsers.add(users.isCurrentUser()) );
        for (int i = 0; i< currentUsers.size();i++){
            if (currentUsers.get(i) == true){
                Users user = userRepository.findById(new Long(i+1)).get();
                user.setCurrUser(false);
                userRepository.save(user);
                break;
            }
        }
    }

}
