package saleksovski.scrum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleksovski.scrum.model.Board;
import saleksovski.scrum.model.Sprint;
import saleksovski.scrum.repository.BoardRepository;
import saleksovski.scrum.repository.SprintRepository;

import java.util.List;

/**
 * Created by stefan on 2/25/16.
 */
@Service
public class SprintService {

    private SprintRepository sprintRepository;
    private BoardRepository boardRepository;

    @Autowired
    public SprintService(SprintRepository sprintRepository, BoardRepository boardRepository) {
        this.sprintRepository = sprintRepository;
        this.boardRepository = boardRepository;
    }

    public List<Sprint> findByBoardSlug(String slug) {
        Board board = boardRepository.findBySlug(slug);
        if (board == null) {
            return null;
        }
        return board.getSprints();
    }

    public Sprint findByBoardSlugSprintId(String slug, Long id) {
        Sprint sprint = sprintRepository.findOne(id);
        if (sprint == null) {
            return null;
        }
        if (sprint.getBoard().getSlug().equals(slug)) {
            return sprint;
        }
        return null;
    }

    public Sprint createSprint(String slug, Sprint sprint) {
        Board board = boardRepository.findBySlug(slug);
        if (board == null) {
            return null;
        }
        sprint.setBoard(board);
        return sprintRepository.save(sprint);
    }

}
