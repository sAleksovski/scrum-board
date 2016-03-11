package saleksovski.scrum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleksovski.auth.SecurityUtil;
import saleksovski.auth.exception.UserNotAuthenticated;
import saleksovski.auth.model.MyUser;
import saleksovski.scrum.model.Sprint;
import saleksovski.scrum.model.Task;
import saleksovski.scrum.repository.SprintRepository;
import saleksovski.scrum.repository.TaskRepository;

import java.util.List;

/**
 * Created by stefan on 2/25/16.
 */
@Service
public class TaskService {

    private SprintRepository sprintRepository;
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(SprintRepository sprintRepository, TaskRepository taskRepository) {
        this.sprintRepository = sprintRepository;
        this.taskRepository = taskRepository;
    }

    public List<Task> findByBoardAndSprint(String slug, Long sprintId) {
        Sprint sprint = sprintRepository.findOne(sprintId);
        if ( ! sprint.getBoard().getSlug().equals(slug)) {
            return null;
        }
        return taskRepository.findBySprint(sprint);
    }

    public Task createTask(String slug, Long sprintId, Task task) {
        Sprint sprint = sprintRepository.findOne(sprintId);

        if (sprint == null || ! sprint.getBoard().getSlug().equals(slug)) {
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
        task.setAssignedTo(user);
        return taskRepository.save(task);
    }

    public Task updateTask(String slug, Long sprintId, Long taskId, Task task) {
        Task oldTask = taskRepository.findOne(taskId);
        if (oldTask == null
                || oldTask.getSprint().getId() != sprintId
                || ! oldTask.getSprint().getBoard().getSlug().equals(slug)) {
            return null;
        }
        oldTask.setName(task.getName());
        oldTask.setAssignedTo(task.getAssignedTo());
        oldTask.setSprint(task.getSprint());
        oldTask.setDescription(task.getDescription());
        oldTask.setTaskProgress(task.getTaskProgress());
        return taskRepository.save(oldTask);
    }
}
