package saleksovski.scrum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleksovski.auth.SecurityUtil;
import saleksovski.auth.exception.UserNotAuthenticated;
import saleksovski.auth.model.MyUser;
import saleksovski.auth.repository.UserRepository;
import saleksovski.scrum.model.Comment;
import saleksovski.scrum.model.Sprint;
import saleksovski.scrum.model.Task;
import saleksovski.scrum.repository.CommentRepository;
import saleksovski.scrum.repository.SprintRepository;
import saleksovski.scrum.repository.TaskRepository;

import java.util.List;
import java.util.Objects;

/**
 * Created by stefan on 2/25/16.
 */
@Service
public class TaskService {

    private SprintRepository sprintRepository;
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    @Autowired
    public TaskService(SprintRepository sprintRepository, TaskRepository taskRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public List<Task> findByBoardAndSprint(String slug, Long sprintId) {
        Sprint sprint = sprintRepository.findOne(sprintId);
        if (!sprint.getBoard().getSlug().equals(slug)) {
            return null;
        }
        return taskRepository.findBySprintOrderByPosition(sprint);
    }

    public Task findOne(String slug, Long sprintId, Long taskId) {
        Task task = taskRepository.findOne(taskId);
        if (task == null) {
            return null;
        }
        if (!Objects.equals(task.getSprint().getId(), sprintId)
                || ! task.getSprint().getBoard().getSlug().equals(slug)) {
            return null;
        }
        return task;
    }

    public Task createTask(String slug, Long sprintId, Task task) {
        Sprint sprint = sprintRepository.findOne(sprintId);

        if (sprint == null || !sprint.getBoard().getSlug().equals(slug)) {
            return null;
        }
        task.setSprint(sprint);
        MyUser user;
        try {
            user = SecurityUtil.getUserDetails();
        } catch (UserNotAuthenticated userNotAuthenticated) {
            userNotAuthenticated.printStackTrace();
            return null;
        }
        task.setCreatedBy(user);
        if (task.getAssignedTo() == null) {
            task.setAssignedTo(user);
        } else {
            MyUser au = userRepository.findByEmail(task.getAssignedTo().getEmail());
            if (au != null) {
                task.setAssignedTo(au);
            } else {
                task.setAssignedTo(user);
            }
        }
        task.setPosition(0f);
        task = taskRepository.save(task);
        task.setPosition(2f * task.getId());
        return taskRepository.save(task);
    }

    public Task updateTask(String slug, Long sprintId, Long taskId, Task task) {
        Task oldTask = taskRepository.findOne(taskId);
        if (oldTask == null
                || !Objects.equals(oldTask.getSprint().getId(), sprintId)
                || !oldTask.getSprint().getBoard().getSlug().equals(slug)) {
            return null;
        }
        oldTask.setName(task.getName());
        if (task.getAssignedTo() != null
                && !Objects.equals(oldTask.getAssignedTo().getId(), task.getAssignedTo().getId())) {
            MyUser au = userRepository.findByEmail(task.getAssignedTo().getEmail());
            if (au != null) {
                oldTask.setAssignedTo(au);
            }
        }
        oldTask.setDescription(task.getDescription());
        oldTask.setProgress(task.getProgress());
        oldTask.setPosition(task.getPosition());
        oldTask.setDifficulty(task.getDifficulty());
        oldTask.setPriority(task.getPriority());
        return taskRepository.save(oldTask);
    }

    public Comment addComment(String slug, Long sprintId, Long taskId, String text, MyUser taskCreator) {
        Task oldTask = taskRepository.findOne(taskId);
        if (oldTask == null
                || !Objects.equals(oldTask.getSprint().getId(), sprintId)
                || !oldTask.getSprint().getBoard().getSlug().equals(slug)) {
            return null;
        }
        Comment comment = new Comment();
        comment.setText(text);
        comment.setTask(oldTask);
        MyUser user;
        comment.setCreator(taskCreator);
        return commentRepository.save(comment);
    }
}
