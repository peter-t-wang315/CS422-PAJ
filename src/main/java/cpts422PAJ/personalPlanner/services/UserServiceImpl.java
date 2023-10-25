package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.repositories.UserRepository;
import org.apache.catalina.User;
import org.hibernate.dialect.BooleanDecoder;
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
        return userRepository.save(users);
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


    public Long notUnique() {
        List<Boolean> currentUsers = new ArrayList<>();
        userRepository.findAll().forEach(users -> currentUsers.add(users.isCurrentUser()));
        for (int i = 0; i < currentUsers.size(); i++) {
            if (currentUsers.get(i) == true) {
                Users user = userRepository.findById(new Long(i + 1)).get();
                //this is the username
                if (user.isAdmin()){
                    return new Long(1000000);
                }
                else {

                    if (isCommonName(user.getFirstName())) {
                        if (m_z(user.getLastName())) {
                            return new Long(10);
                        }
                        return new Long(5);
                    }

                    userRepository.save(user);
                }
            }
        }
        return  new Long(-1);
    }

    public Boolean checkSameUser(String newUser)
        {
        List<String> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(user.getUserName()) );

        for(int i = 0; i< users.size();i++){
            if (newUser.equals(users.get(i)))
            {
                return false;
            }
        }
        return true;
    }

    public String getAdminPassword(){
        Users test = userRepository.findById(new Long(1)).get();
        return test.getAdminPassword();
    }

    public void addAllTasksAdmin(){
        //gets the active user
        Users test = userRepository.findById(findActiveUser()).get();
        //if the user is not an admin it returns
        if (!test.isAdmin()){
            return;
        }

    }

    public boolean checkIfAdmin(){
        Long userIdLog = findActiveUser();
        Users tempUsr = userRepository.findById(userIdLog).get();
        //This means user is an admin
        if(tempUsr.isAdmin()){
            return true;
        }else{ //this means that this user is not an admin
            return false;
        }
    }

    public Boolean checkAtSign(String email){
        if(email.indexOf("@") == -1){
            System.out.println("There is no @ in the email");
            return false;
        }
        else{
            System.out.println("There is a @ in the email, you may continue!");
            return true;
        }

    }

    public Boolean checkDomains(String email){

        Set<String> domainSet = new HashSet<String>();

        domainSet.add("wsu.edu");
        domainSet.add("gmail.com");
        domainSet.add("aol.com");
        domainSet.add("proton.me");
        domainSet.add("icloud.com");
        domainSet.add("yahoo.com");
        domainSet.add("live.com");
        domainSet.add("mail.ru");
        domainSet.add("outlook.com");
        domainSet.add("gmx.net");
        domainSet.add("hotmail.com");
        domainSet.add("mail.com");

        for (String domain : domainSet){
            System.out.println(domain);
            if (email.contains(domain)){
                System.out.println(email+ ' ' + domain);
                return true;
            }
        }

        return false;


    }

    public Boolean checkEmail(String email){
        boolean signs = checkAtSign(email);
        boolean domains = checkDomains(email);

        if (signs && domains){
            return true;
        }
        else if(!signs || !domains){
            return false;
        }
        else if (!signs || domains){
            return false;
        }
        else if (signs || !domains){
            return false;
        }
        else{
            return false;
        }



    }











}
