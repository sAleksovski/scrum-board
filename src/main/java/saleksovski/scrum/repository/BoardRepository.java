package saleksovski.scrum.repository;

import org.springframework.data.repository.CrudRepository;
import saleksovski.auth.model.MyUser;
import saleksovski.scrum.model.Board;

import java.util.List;

/**
 * Created by stefan on 2/20/16.
 */
public interface BoardRepository extends CrudRepository<Board, Long> {

    Board findBySlug(String slug);

    List<Board> findByBoardUserRoleUser(MyUser user);
}
