package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saleksovski.auth.SecurityUtil;
import saleksovski.auth.exception.UserNotAuthenticated;
import saleksovski.scrum.model.Comment;
import saleksovski.scrum.model.Notification;
import saleksovski.scrum.model.Task;
import saleksovski.scrum.model.enums.NotificationType;
import saleksovski.scrum.service.NotificationService;
import saleksovski.scrum.service.TaskService;
import saleksovski.scrum.service.WebSocketService;

import java.util.List;

/**
 * Created by stefan on 2/25/16.
 */
@RestController
@RequestMapping(value = "/api/boards/{slug}/sprints/{sprintId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private WebSocketService webSocketService;

    @RequestMapping
    public ResponseEntity<List<Task>> index(@PathVariable String slug, @PathVariable Long sprintId) {
        List<Task> tasks = taskService.findByBoardAndSprint(slug, sprintId);
        if (tasks == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @RequestMapping(value = "/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable String slug, @PathVariable Long sprintId, @PathVariable Long taskId) {
        Task task = taskService.findOne(slug, sprintId, taskId);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Task> createTask(@PathVariable String slug, @PathVariable Long sprintId, @RequestBody Task task) throws UserNotAuthenticated {
        Task newTask = taskService.createTask(slug, sprintId, task);
        if (newTask == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (newTask.getCreatedBy().getId() != newTask.getAssignedTo().getId()) {
            String url = "#/b/" + slug + "/tasks/" + sprintId + "-" + newTask.getId();
            Notification notification = notificationService.createNotification(
                    SecurityUtil.getUserDetails(),
                    newTask.getAssignedTo(),
                    NotificationType.АSSIGNED_TASK,
                    null,
                    null,
                    newTask,
                    url);

            webSocketService.sendNotification(newTask.getAssignedTo().getEmail(), notification);
        }
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask(@PathVariable String slug, @PathVariable Long sprintId, @PathVariable Long taskId, @RequestBody Task task) {
        Task newTask = taskService.updateTask(slug, sprintId, taskId, task);
        if (newTask == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{taskId}/comments", method = RequestMethod.POST)
    public ResponseEntity<Comment> addComment(@PathVariable String slug, @PathVariable Long sprintId, @PathVariable Long taskId, @RequestBody String comment) throws UserNotAuthenticated {
        Comment newComment = taskService.addComment(slug, sprintId, taskId, comment, SecurityUtil.getUserDetails());
        if (newComment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String url = "#/b/" + slug + "/tasks/" + sprintId + "-" + taskId;
        List<Notification> notifications = notificationService.createAddCommentNotifications(newComment, url, SecurityUtil.getUserDetails());

        notifications.forEach(n -> webSocketService.sendNotification(n.getUser().getEmail(), n));

        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

}
