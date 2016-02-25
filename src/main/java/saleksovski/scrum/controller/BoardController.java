package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saleksovski.scrum.auth.SecurityUtil;
import saleksovski.scrum.auth.exception.UserNotAuthenticated;
import saleksovski.scrum.auth.model.MyUser;
import saleksovski.scrum.enums.UserRole;
import saleksovski.scrum.model.Board;
import saleksovski.scrum.service.BoardService;

/**
 * Created by stefan on 2/20/16.
 */
@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    BoardService boardService;

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<Iterable<Board>> boards() {
        try {
            return new ResponseEntity<>(boardService.findByUser(SecurityUtil.getUserDetails()), HttpStatus.OK);
        } catch (UserNotAuthenticated userNotAuthenticated) {
            userNotAuthenticated.printStackTrace();
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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
    ResponseEntity<Board> editBoard(@PathVariable String slug, @RequestBody MyUser user) {
        return new ResponseEntity<>(boardService.addUserToBoard(slug, user, UserRole.ROLE_USER), HttpStatus.OK);
    }

}
