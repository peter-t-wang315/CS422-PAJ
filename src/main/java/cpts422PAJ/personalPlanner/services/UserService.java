package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Users;

import java.util.List;

public interface UserService {
    public Iterable<Users> findAll();

    public Users save(Users users);

    public boolean isUser(String name, String pass);
    public List getNames();
}
