package saleksovski.scrum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleksovski.scrum.auth.SecurityUtil;
import saleksovski.scrum.auth.exception.UserNotAuthenticated;
import saleksovski.scrum.auth.model.MyUser;
import saleksovski.scrum.auth.repository.UserRepository;
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

    private UserRepository userRepository;
    private BoardRepository boardRepository;
    private BoardUserRoleRepository boardUserRoleRepository;

    @Autowired
    public BoardService(UserRepository userRepository, BoardRepository boardRepository, BoardUserRoleRepository boardUserRoleRepository) {
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.boardUserRoleRepository = boardUserRoleRepository;
    }

    public List<Board> findByUser(MyUser user) {
        return boardRepository.findByBoardUserRoleUser(user);
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
        board.setSlug(slug);
        board = boardRepository.save(board);
        BoardUserRole boardUserRole = new BoardUserRole();
        boardUserRole.setBoard(board);
        boardUserRole.setUser(SecurityUtil.getUserDetails());
        boardUserRole.setRole(UserRole.ROLE_ADMIN);
        boardUserRoleRepository.save(boardUserRole);
        return board;
    }

    public Board updateBoard(Board board) {
        Board myBoard = boardRepository.findBySlug(board.getSlug());
        if (myBoard != null) {
            myBoard.setName(board.getName());
            return boardRepository.save(myBoard);
        }
        return null;
    }

    public Board addUserToBoard(String slug, MyUser user, UserRole userRole) {
        Board myBoard = boardRepository.findBySlug(slug);
        MyUser myUser = userRepository.findByEmail(user.getEmail());
        BoardUserRole boardUserRole = boardUserRoleRepository.findByBoardAndUserAndRole(myBoard, myUser, userRole);
        if (boardUserRole == null) {
            boardUserRole = new BoardUserRole();
            boardUserRole.setBoard(myBoard);
            boardUserRole.setUser(myUser);
            boardUserRole.setRole(userRole);
            boardUserRoleRepository.save(boardUserRole);
        }
        return myBoard;
    }

}
