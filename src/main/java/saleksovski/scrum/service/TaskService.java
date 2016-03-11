package saleksovski.scrum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleksovski.scrum.model.Board;
import saleksovski.scrum.model.Sprint;
import saleksovski.scrum.model.Task;
import saleksovski.scrum.repository.BoardRepository;
import saleksovski.scrum.repository.SprintRepository;
import saleksovski.scrum.repository.TaskRepository;

import java.util.List;

/**
 * Created by stefan on 2/25/16.
 */
@Service
public class TaskService {

    private BoardRepository boardRepository;
    private SprintRepository sprintRepository;
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(BoardRepository boardRepository, SprintRepository sprintRepository, TaskRepository taskRepository) {
        this.boardRepository = boardRepository;
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
    }

    public List<Task> findByBoardAndSprint(String slug, Long sprintId) {
        Sprint sprint = sprintRepository.findOne(sprintId);
        Board board = boardRepository.findBySlug(slug);
        if (sprint.getBoard().getId() != board.getId()) {
            return null;
        }
        return taskRepository.findBySprint(sprint);
    }

}
