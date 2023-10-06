package cpts422PAJ.personalPlanner.repositories;

import cpts422PAJ.personalPlanner.entities.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TaskRepository extends CrudRepository<Task,Long> {
    @Query("SELECT t FROM Task t WHERE t.u.id = :userId")
    List<Task> findTasksByUserId(@Param("userId")Long userId);
    @Query("SELECT t FROM Task t WHERE t.tag.id = :tagId")
    List<Task> findTasksByTagId(Long tagId);
}
