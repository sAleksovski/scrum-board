package saleksovski.scrum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleksovski.auth.SecurityUtil;
import saleksovski.auth.exception.UserNotAuthenticated;
import saleksovski.auth.model.MyUser;
import saleksovski.auth.repository.UserRepository;
import saleksovski.scrum.model.enums.UserRole;
import saleksovski.scrum.model.Board;
import saleksovski.scrum.model.BoardUserRole;
import saleksovski.scrum.repository.BoardRepository;
import saleksovski.scrum.repository.BoardUserRoleRepository;
import saleksovski.scrum.service.utils.StringUtils;

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
        if (myBoard == null) {
            return null;
        }
        myBoard.setName(board.getName());
        return boardRepository.save(myBoard);
    }

    public List<BoardUserRole> addUserToBoard(String slug, MyUser user, UserRole userRole) throws UserNotAuthenticated {
        Board myBoard = boardRepository.findBySlug(slug);
        MyUser myUser = userRepository.findByEmail(user.getEmail());
        if (myBoard == null
                || myUser == null
                || userRole == null) {
            return null;
        }

        MyUser me = SecurityUtil.getUserDetails();
        for (BoardUserRole bur :
                myBoard.getBoardUserRole()) {

            if (me.getId() == bur.getUser().getId() && bur.getRole() == UserRole.ROLE_USER) {
                return myBoard.getBoardUserRole();
            }
        }

        BoardUserRole boardUserRole = boardUserRoleRepository.findByBoardAndUser(myBoard, myUser);
        if (boardUserRole == null) {
            boardUserRole = new BoardUserRole();
            boardUserRole.setBoard(myBoard);
            boardUserRole.setUser(myUser);
        }

        boolean adminToUser = false;
        int admins = 0;

        for (BoardUserRole bur :
                myBoard.getBoardUserRole()) {

            if (bur.getRole() == UserRole.ROLE_ADMIN && bur.getUser().getId() == myUser.getId() && userRole == UserRole.ROLE_USER) {
                adminToUser = true;
            }
            if (bur.getRole() == UserRole.ROLE_ADMIN) {
                admins++;
            }
        }

        if (admins == 0 || (admins == 1 && adminToUser)) {
            boardUserRole.setRole(UserRole.ROLE_ADMIN);
        } else {
            boardUserRole.setRole(userRole);
        }

        boardUserRoleRepository.save(boardUserRole);
        return myBoard.getBoardUserRole();
    }

    public boolean deleteUserFromBoard(MyUser currentUser, String slug, Long boardUserRole) {
        Board board = findBySlug(slug);

        boolean canDelete = false;

        int admins = 0;

        BoardUserRole boardUserRoleToDelete = null;

        for (BoardUserRole bur :
                board.getBoardUserRole()) {
            if (bur.getUser().getId() == currentUser.getId() && bur.getRole() == UserRole.ROLE_ADMIN) {
                canDelete = true;
            }

            if (bur.getRole() == UserRole.ROLE_ADMIN) {
                admins++;
            }

            if (bur.getId() == boardUserRole) {
                boardUserRoleToDelete = bur;
            }
        }

        if (!canDelete) {
            return false;
        }

        if (boardUserRoleToDelete != null && boardUserRoleToDelete.getRole() == UserRole.ROLE_ADMIN && admins < 2) {
            return false;
        }

        boardUserRoleRepository.delete(boardUserRoleToDelete);
        return true;
    }
}
