package cpts422PAJ.personalPlanner.Repositories;

import cpts422PAJ.personalPlanner.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
