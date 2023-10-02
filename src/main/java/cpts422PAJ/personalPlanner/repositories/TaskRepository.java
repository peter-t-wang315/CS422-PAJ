package cpts422PAJ.personalPlanner.repositories;

import cpts422PAJ.personalPlanner.entities.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Date;

public interface TaskRepository extends CrudRepository<Task,Long> {
    @Query("SELECT t FROM Task t WHERE t.u.id = :userId")
    List<Task> findTasksByUserId(Long userId);

    // Custom queries to fetch tasks by status and due date
    List<Task> findTasksByUserIdAndStatus(Long userId, String status);

    List<Task> findTasksByUserIdAndStatusAndDueDate(Long userId, String status, Date dueDate);

    List<Task> findTasksByUserIdAndStatusAndDueDateBetween(Long userId, String status, Date startDate, Date endDate);
}
