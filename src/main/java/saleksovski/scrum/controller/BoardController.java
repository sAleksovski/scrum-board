package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import saleksovski.scrum.auth.SecurityUtil;
import saleksovski.scrum.auth.exception.UserNotAuthenticated;
import saleksovski.scrum.enums.BoardUserRole;
import saleksovski.scrum.model.Board;
import saleksovski.scrum.service.BoardService;
import saleksovski.scrum.utils.StringUtils;

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
        Board board = new Board();
        board.setName(name);
        board.setSlug(StringUtils.randomString(8));
        board.getUsers().add(SecurityUtil.getUserDetails());
//        board.getUserRoles().put(SecurityUtil.getUserDetails(), BoardUserRole.ROLE_ADMIN);
        return boardService.save(board);
    }

    @RequestMapping(value = "/editboard")
    public
    @ResponseBody
    Board editBoard() throws UserNotAuthenticated {
        Board b = boardService.findBySlug("bHfSYRGl");
        b.getUsers().add(SecurityUtil.getUserDetails());
//        b.getUserRoles().put(SecurityUtil.getUserDetails(), BoardUserRole.ROLE_USER);
        b = boardService.save(b);
        return b;
    }

}
