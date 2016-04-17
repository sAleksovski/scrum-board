package saleksovski.scrum.repository;

import org.springframework.data.repository.CrudRepository;
import saleksovski.auth.model.MyUser;
import saleksovski.scrum.model.enums.UserRole;
import saleksovski.scrum.model.Board;
import saleksovski.scrum.model.BoardUserRole;

/**
 * Created by stefan on 2/20/16.
 */
public interface BoardUserRoleRepository extends CrudRepository<BoardUserRole, Long> {

    BoardUserRole findByBoardAndUser(Board board, MyUser user);
}
