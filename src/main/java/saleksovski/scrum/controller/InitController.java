package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saleksovski.auth.SecurityUtil;
import saleksovski.auth.exception.UserNotAuthenticated;
import saleksovski.auth.model.MyUser;
import saleksovski.scrum.model.*;
import saleksovski.scrum.model.enums.NotificationType;
import saleksovski.scrum.model.enums.TaskDifficulty;
import saleksovski.scrum.model.enums.TaskPriority;
import saleksovski.scrum.model.enums.TaskProgress;
import saleksovski.scrum.repository.CommentRepository;
import saleksovski.scrum.service.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stefan on 3/12/16.
 */
@RestController
public class InitController {

    @Autowired
    BoardService boardService;

    @Autowired
    SprintService sprintService;

    @Autowired
    TaskService taskService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    WebSocketService webSocketService;

    @Autowired
    NotificationService notificationService;

    @RequestMapping("/api/ws")
    public String ws() throws UserNotAuthenticated {
        MyUser user = SecurityUtil.getUserDetails();

        Map<String, String> map = new HashMap<>();
        map.put("read", "true");
        map.put("content", "Hello World");
        webSocketService.sendNotification(user.getEmail(), map);

        map = new HashMap<>();
        map.put("read", "false");
        map.put("content", "Unread message");
        webSocketService.sendNotification(user.getEmail(), map);

        return user.getEmail();
    }

    @RequestMapping("/api/nf")
    public Notification nf() throws UserNotAuthenticated {
        MyUser user = SecurityUtil.getUserDetails();

        MyUser to = new MyUser();
        to.setId(1L);

        Task task = taskService.findOne("jVZgP8R7", 2L, 7L);

        return notificationService.createNotification(user, to, NotificationType.COMMENTED_ON_TASK, null, null, task);
    }

    @RequestMapping("/api/init")
    public String init() throws UserNotAuthenticated {

        MyUser user = SecurityUtil.getUserDetails();

        Board board = boardService.findByUser(user).get(0);
        Sprint sprint = sprintService.findByBoardSlug(board.getSlug()).get(0);

        Task task = new Task();
        task.setName("Task TODO");
        task.setDescription("Description 1");
        task.setCreatedBy(user);
        task.setAssignedTo(user);
        task.setSprint(sprint);
        task.setProgress(TaskProgress.TODO);
        task.setPriority(TaskPriority.LOW);
        task.setDifficulty(TaskDifficulty._0);
        task = taskService.createTask(board.getSlug(), sprint.getId(), task);

        Comment comment = new Comment();
        comment.setCreator(user);
        comment.setTask(task);
        comment.setText("lorem ipsum");
        commentRepository.save(comment);

        comment = new Comment();
        comment.setCreator(user);
        comment.setTask(task);
        comment.setText("lorem ipsum 2");
        commentRepository.save(comment);

        task = new Task();
        task.setName("Task IN_PROGRESS");
        task.setDescription("Description 2");
        task.setCreatedBy(user);
        task.setAssignedTo(user);
        task.setSprint(sprint);
        task.setProgress(TaskProgress.IN_PROGRESS);
        task.setPriority(TaskPriority.MEDIUM);
        task.setDifficulty(TaskDifficulty._1);
        taskService.createTask(board.getSlug(), sprint.getId(), task);

        task = new Task();
        task.setName("Task BLOCKED");
        task.setDescription("Description 3");
        task.setCreatedBy(user);
        task.setAssignedTo(user);
        task.setSprint(sprint);
        task.setProgress(TaskProgress.BLOCKED);
        task.setPriority(TaskPriority.HIGH);
        task.setDifficulty(TaskDifficulty._2);
        taskService.createTask(board.getSlug(), sprint.getId(), task);

        task = new Task();
        task.setName("Task TESTING");
        task.setDescription("Description 4");
        task.setCreatedBy(user);
        task.setAssignedTo(user);
        task.setSprint(sprint);
        task.setProgress(TaskProgress.TESTING);
        task.setPriority(TaskPriority.URGENT);
        task.setDifficulty(TaskDifficulty._3);
        taskService.createTask(board.getSlug(), sprint.getId(), task);

        task = new Task();
        task.setName("Task DONE");
        task.setDescription("Description 5");
        task.setCreatedBy(user);
        task.setAssignedTo(user);
        task.setSprint(sprint);
        task.setProgress(TaskProgress.DONE);
        task.setPriority(TaskPriority.MEDIUM);
        task.setDifficulty(TaskDifficulty._5);
        taskService.createTask(board.getSlug(), sprint.getId(), task);



        task = new Task();
        task.setName("Task TODO 2");
        task.setDescription("Description 6");
        task.setCreatedBy(user);
        task.setAssignedTo(user);
        task.setSprint(sprint);
        task.setProgress(TaskProgress.TODO);
        task.setPriority(TaskPriority.MEDIUM);
        task.setDifficulty(TaskDifficulty._8);
        taskService.createTask(board.getSlug(), sprint.getId(), task);

        task = new Task();
        task.setName("Task IN_PROGRESS 2");
        task.setDescription("Description 7");
        task.setCreatedBy(user);
        task.setAssignedTo(user);
        task.setSprint(sprint);
        task.setProgress(TaskProgress.IN_PROGRESS);
        task.setPriority(TaskPriority.MEDIUM);
        task.setDifficulty(TaskDifficulty._13);
        taskService.createTask(board.getSlug(), sprint.getId(), task);

        task = new Task();
        task.setName("Task BLOCKED 2");
        task.setDescription("Description 8");
        task.setCreatedBy(user);
        task.setAssignedTo(user);
        task.setSprint(sprint);
        task.setProgress(TaskProgress.BLOCKED);
        task.setPriority(TaskPriority.MEDIUM);
        task.setDifficulty(TaskDifficulty._21);
        taskService.createTask(board.getSlug(), sprint.getId(), task);

        task = new Task();
        task.setName("Task TESTING 2");
        task.setDescription("Description 9");
        task.setCreatedBy(user);
        task.setAssignedTo(user);
        task.setSprint(sprint);
        task.setProgress(TaskProgress.TESTING);
        task.setPriority(TaskPriority.MEDIUM);
        task.setDifficulty(TaskDifficulty._34);
        taskService.createTask(board.getSlug(), sprint.getId(), task);

        task = new Task();
        task.setName("Task DONE 2");
        task.setDescription("Description 10");
        task.setCreatedBy(user);
        task.setAssignedTo(user);
        task.setSprint(sprint);
        task.setProgress(TaskProgress.DONE);
        task.setPriority(TaskPriority.MEDIUM);
        task.setDifficulty(TaskDifficulty._55);
        taskService.createTask(board.getSlug(), sprint.getId(), task);

        return "Done";
    }

}
