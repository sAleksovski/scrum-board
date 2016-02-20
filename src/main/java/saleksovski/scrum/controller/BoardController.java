package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import saleksovski.scrum.auth.SecurityUtil;
import saleksovski.scrum.auth.exception.UserNotAuthenticated;
import saleksovski.scrum.enums.UserRole;
import saleksovski.scrum.model.Board;
import saleksovski.scrum.model.BoardUserRole;
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
    Iterable<Board> boards() {
        try {
            return boardService.findByUser(SecurityUtil.getUserDetails());
        } catch (UserNotAuthenticated userNotAuthenticated) {
            userNotAuthenticated.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/{slug}", method = RequestMethod.GET)
    public
    @ResponseBody
    Board boardBySlug(@PathVariable String slug) {
        return boardService.findBySlug(slug);
    }

    @RequestMapping(method = RequestMethod.POST)
    public
    @ResponseBody
    Board saveBoard(@RequestBody String name) throws UserNotAuthenticated {
        return boardService.createBoard(name);
    }

    @RequestMapping(value = "/editboard")
    public
    @ResponseBody
    Board editBoard() throws UserNotAuthenticated {
        Board board = boardService.findBySlug("TmQrV1iA");
        BoardUserRole boardUserRole = new BoardUserRole();
        boardUserRole.setRole(UserRole.ROLE_USER);
        boardUserRole.setUser(SecurityUtil.getUserDetails());
        boardUserRole = boardService.saveBoardUserRole(boardUserRole);
        board.getBoardUserRole().add(boardUserRole);
        board = boardService.save(board);
        return board;
    }

}
