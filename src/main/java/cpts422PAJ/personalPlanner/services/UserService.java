package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Users;

public interface UserService {
    public Iterable<Users> findAll();

    public Users save(Users users);
}
