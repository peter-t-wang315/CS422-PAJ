package cpts422PAJ.personalPlanner.repositories;

import cpts422PAJ.personalPlanner.entities.Task;
import org.springframework.data.repository.CrudRepository;


public interface TaskRepository extends CrudRepository<Task,Long> {
}
