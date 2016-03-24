package saleksovski.scrum.repository;

import org.springframework.data.repository.CrudRepository;
import saleksovski.scrum.model.Comment;

/**
 * Created by stefan on 3/24/16.
 */
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
