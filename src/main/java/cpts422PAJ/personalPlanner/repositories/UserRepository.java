package cpts422PAJ.personalPlanner.repositories;

import cpts422PAJ.personalPlanner.entities.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Long> {
}
