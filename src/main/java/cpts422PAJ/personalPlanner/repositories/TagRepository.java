package cpts422PAJ.personalPlanner.repositories;

import cpts422PAJ.personalPlanner.entities.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {
  //  @Query("SELECT t FROM Task t WHERE t.u.id = :userId")
}
