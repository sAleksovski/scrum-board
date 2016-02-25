package saleksovski.scrum.repository;

import org.springframework.data.repository.CrudRepository;
import saleksovski.scrum.auth.model.MyUser;
import saleksovski.scrum.enums.UserRole;
import saleksovski.scrum.model.Board;
import saleksovski.scrum.model.BoardUserRole;

/**
 * Created by stefan on 2/20/16.
 */
public interface BoardUserRoleRepository extends CrudRepository<BoardUserRole, Long> {

    BoardUserRole findByBoardAndUserAndRole(Board board, MyUser user, UserRole role);
}
