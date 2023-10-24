package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Users;

import java.util.List;

public interface UserService {
    public Iterable<Users> findAll();

    public Users getUserById(Long id);

    public Users save(Users users);

    public boolean isUser(String name, String pass);
    public void changeUserActivity(String name, String pass);

    public Long findActiveUser();

    public void logOffUser();

    public void logOffAllUsers();


    public boolean isCommonName(String usrName);
    public boolean m_z(String lastName);

    public Long notUnique();

    public Boolean checkSameUser(String newUser);
    public String getAdminPassword();
    public void addAllTasksAdmin();
    public boolean checkIfAdmin();

    public Boolean notUniqueRedirection();
    public Boolean checkAtSign(String email);

    public Boolean checkDomains(String email);

    public Boolean checkEmail(String email);
}
