package cpts422PAJ.personalPlanner.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Users {
    private String email;
    private String userName;
    private String firstName;
    private String lastName;

    public String getPassword() {
        return userPassword;
    }

    public boolean isCurrentUser() {
        return currUser;
    }

    private String userPassword;

    private boolean currUser;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    //If true it is an admin user and if it is a normal user
    private boolean isAdmin;


    public String getAdminPassword() {
        return adminPassword;
    }

    private final String adminPassword = "GoCougs";




    private  String userAdminPassword;

//    @OneToMany
//    private ArrayList<Task> tasks;
// If you want to add these back have to redo string method and getter

    @Id
    @GeneratedValue
    private Long id;


    public Users(String email, String userName, String userPassword,String firstName, String lastName, Boolean isAdmin) {
        this.email = email;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userPassword = userPassword;
        this.currUser = false;
        this.isAdmin = isAdmin;
//        tasks = new ArrayList<Task>();
    }

    public Users() {

    }


    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }
    @Override
    public String toString() {
        return "Users{" +
                "email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", currentUser=" + currUser +
                ", id=" + id +
                '}';
    }
    public void setCurrUser(boolean currUser){
        this.currUser = currUser;
    }
}
