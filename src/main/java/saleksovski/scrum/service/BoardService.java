package saleksovski.scrum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleksovski.scrum.auth.model.MyUser;
import saleksovski.scrum.model.Board;
import saleksovski.scrum.repository.BoardRepository;

import java.util.List;

/**
 * Created by stefan on 2/20/16.
 */
@Service
public class BoardService {

    private BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Iterable<Board> findAll() {
        return boardRepository.findAll();
    }

    public Board save(Board board) {
        return boardRepository.save(board);
    }

    public List<Board> findByUser(MyUser user) {
        return boardRepository.findByUsers(user);
    }

    public Board findBySlug(String slug) {
        return boardRepository.findBySlug(slug);
    }

}
