package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public void logOffAllUsers(){
        List<Boolean> currentUsers = new ArrayList<>();
        userRepository.findAll().forEach(users -> currentUsers.add(users.isCurrentUser()) );
        for (int i = 0; i< currentUsers.size();i++){
            if (currentUsers.get(i) == true){
                Users user = userRepository.findById(new Long(i+1)).get();
                user.setCurrUser(false);
                userRepository.save(user);

            }
        }
    }

    //This code checks if the user is common
    public boolean isCommonName(String usrName){

        Set<String> nameSet = new HashSet<String>();

        nameSet.addAll(Arrays.asList(new String[]{
        "John", "Michael", "Robert", "David", "James", "Mary",
        "William", "Richard", "Thomas", "Linda", "Mark", "Charles",
        "Joseph", "Patricia", "Barbara", "Jennifer", "Paul",
        "Maria", "Susan", "Daniel"
        }));

        return nameSet.contains(usrName);


    }

    // This checks if the last name is
    public boolean m_z(String lastName){

        char firstLetter = lastName.charAt(0);

        Set<Character> letterSet = new HashSet<Character>();

        letterSet.addAll(Arrays.asList(new Character[]{
                'M', 'N', 'O', 'P', 'Q','R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z'

        }));


        return letterSet.contains(firstLetter);

    }


    public Boolean notUnique(){
        List<Boolean> currentUsers = new ArrayList<>();
        userRepository.findAll().forEach(users -> currentUsers.add(users.isCurrentUser()) );
        for (int i = 0; i< currentUsers.size();i++){
            if (currentUsers.get(i) == true){
                Users user = userRepository.findById(new Long(i+1)).get();
                //this is the username
                if(isCommonName(user.getFirstName())){
                    if (m_z(user.getLastName())){
                        return true;

                    }
                }

                userRepository.save(user);

            }
        }
        return false;
    }











}
