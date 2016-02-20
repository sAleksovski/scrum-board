package saleksovski.scrum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleksovski.scrum.auth.SecurityUtil;
import saleksovski.scrum.auth.exception.UserNotAuthenticated;
import saleksovski.scrum.auth.model.MyUser;
import saleksovski.scrum.enums.UserRole;
import saleksovski.scrum.model.Board;
import saleksovski.scrum.model.BoardUserRole;
import saleksovski.scrum.repository.BoardRepository;
import saleksovski.scrum.repository.BoardUserRoleRepository;
import saleksovski.scrum.utils.StringUtils;

import java.util.List;

/**
 * Created by stefan on 2/20/16.
 */
@Service
public class BoardService {

    private BoardRepository boardRepository;
    private BoardUserRoleRepository boardUserRoleRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, BoardUserRoleRepository boardUserRoleRepository) {
        this.boardRepository = boardRepository;
        this.boardUserRoleRepository = boardUserRoleRepository;
    }

    public Iterable<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board save(Board board) {
        return boardRepository.save(board);
    }

    public List<Board> findByUser(MyUser user) {
        return boardRepository.findByboardUserRoleUser(user);
    }

    public Board findBySlug(String slug) {
        return boardRepository.findBySlug(slug);
    }

    public Board createBoard(String name) throws UserNotAuthenticated {
        Board board = new Board();
        board.setName(name);
        String slug = StringUtils.randomString(8);
        while (findBySlug(slug) != null) {
            slug = StringUtils.randomString(8);
        }
        board.setSlug(StringUtils.randomString(8));
        BoardUserRole boardUserRole = new BoardUserRole();
        boardUserRole.setRole(UserRole.ROLE_ADMIN);
        boardUserRole.setUser(SecurityUtil.getUserDetails());
        boardUserRole = boardUserRoleRepository.save(boardUserRole);
        board.getBoardUserRole().add(boardUserRole);
        return boardRepository.save(board);
    }

    public BoardUserRole saveBoardUserRole(BoardUserRole boardUserRole) {
        return boardUserRoleRepository.save(boardUserRole);
    }

}
