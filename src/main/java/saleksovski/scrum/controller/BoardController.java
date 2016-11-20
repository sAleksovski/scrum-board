package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saleksovski.auth.SecurityUtil;
import saleksovski.auth.exception.UserNotAuthenticated;
import saleksovski.auth.model.MyUser;
import saleksovski.scrum.model.Board;
import saleksovski.scrum.model.BoardUserRole;
import saleksovski.scrum.model.Notification;
import saleksovski.scrum.model.enums.NotificationType;
import saleksovski.scrum.model.enums.UserRole;
import saleksovski.scrum.service.BoardService;
import saleksovski.scrum.service.NotificationService;
import saleksovski.scrum.service.WebSocketService;

import java.util.List;

/**
 * Created by stefan on 2/20/16.
 */
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    WebSocketService webSocketService;

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<Iterable<Board>> boards() throws UserNotAuthenticated {
        return new ResponseEntity<>(boardService.findByUser(SecurityUtil.getUserDetails()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<Board> boardBySlug(@PathVariable String slug) {
        Board board = boardService.findBySlug(slug);
        if (board != null) {
            return new ResponseEntity<>(board, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<Board> saveBoard(@RequestBody String name) throws UserNotAuthenticated {
        return new ResponseEntity<>(boardService.createBoard(name), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.PUT)
    public
    @ResponseBody
    ResponseEntity<Board> editBoard(@PathVariable String slug, @RequestBody Board board) {
        if (slug.equals(board.getSlug())) {
            Board myBoard = boardService.updateBoard(board);
            if (myBoard != null) {
                return new ResponseEntity<>(boardService.updateBoard(board), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{slug}/users", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<List<BoardUserRole>> addUserToBoard(@PathVariable String slug, @RequestBody MyUser user) throws UserNotAuthenticated {
        Notification notification = notificationService.createNotification(
                SecurityUtil.getUserDetails(),
                user,
                NotificationType.ADDED_TO_BOARD,
                boardService.findBySlug(slug),
                null,
                null,
                "#/b/" + slug);
        webSocketService.sendNotification(user.getEmail(), notification);
        return new ResponseEntity<>(boardService.addUserToBoard(slug, user, UserRole.ROLE_USER), HttpStatus.OK);
    }

    @RequestMapping(value = "/{slug}/users", method = RequestMethod.PUT)
    public
    @ResponseBody
    ResponseEntity<List<BoardUserRole>> editUserInBoard(@PathVariable String slug, @RequestBody BoardUserRole boardUserRole) throws UserNotAuthenticated {
        List<BoardUserRole> newBoardUserRole = boardService.addUserToBoard(slug, boardUserRole.getUser(), boardUserRole.getRole());
        boolean removed = true;
        boolean isAdmin = false;
        for (BoardUserRole bur :
                newBoardUserRole) {
            if (bur.getUser().getId() == boardUserRole.getUser().getId()) {
                removed = false;
                if (bur.getRole() == UserRole.ROLE_ADMIN) {
                    isAdmin = true;
                }
            }
        }

        NotificationType nf;

        if (removed) {
            nf = NotificationType.REMOVED_FROM_BOARD;
        } else if (isAdmin) {
            nf = NotificationType.MADE_ADMIN;
        } else {
            nf = NotificationType.REMOVED_ADMIN;
        }

        Notification notification = notificationService.createNotification(
                SecurityUtil.getUserDetails(),
                boardUserRole.getUser(),
                nf,
                boardService.findBySlug(slug),
                null,
                null,
                "#/b/" + slug);
        webSocketService.sendNotification(boardUserRole.getUser().getEmail(), notification);

        return new ResponseEntity<>(newBoardUserRole, HttpStatus.OK);
    }

    @RequestMapping(value = "/{slug}/users/{boardUserRole}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity<Boolean> deleteUserInBoard(@PathVariable String slug, @PathVariable Long boardUserRole) throws UserNotAuthenticated {
        boolean deleted = boardService.deleteUserFromBoard(SecurityUtil.getUserDetails(), slug, boardUserRole);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

}
