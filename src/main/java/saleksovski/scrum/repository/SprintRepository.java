package saleksovski.scrum.repository;

import org.springframework.data.repository.CrudRepository;
import saleksovski.scrum.model.Sprint;

/**
 * Created by stefan on 2/25/16.
 */
public interface SprintRepository extends CrudRepository<Sprint, Long> {
}
