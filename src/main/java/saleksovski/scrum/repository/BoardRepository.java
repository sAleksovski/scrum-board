package saleksovski.scrum.repository;

import org.springframework.data.repository.CrudRepository;
import saleksovski.scrum.auth.model.MyUser;
import saleksovski.scrum.model.Board;

import java.util.List;

/**
 * Created by stefan on 2/20/16.
 */
public interface BoardRepository extends CrudRepository<Board, Long> {

    Board findBySlug(String slug);

//    @Query("select b from Board b join b.users id where ?1 in (VALUE(id))")
    List<Board> findByBoardUserRoleUser(MyUser user);
}
