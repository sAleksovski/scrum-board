package saleksovski.scrum.repository;

import org.springframework.data.repository.CrudRepository;
import saleksovski.scrum.model.Sprint;
import saleksovski.scrum.model.Task;

import java.util.List;

/**
 * Created by stefan on 2/25/16.
 */
public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findBySprint(Sprint sprint);

    List<Task> findBySprintOrderByPosition(Sprint sprint);
}
