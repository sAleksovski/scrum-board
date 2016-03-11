package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saleksovski.scrum.model.Task;
import saleksovski.scrum.service.TaskService;

import java.util.List;

/**
 * Created by stefan on 2/25/16.
 */
@RestController
@RequestMapping(value = "/api/boards/{slug}/sprints/{sprintId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping
    public ResponseEntity<List<Task>> index(@PathVariable String slug, @PathVariable Long sprintId) {
        List<Task> tasks = taskService.findByBoardAndSprint(slug, sprintId);
        if (tasks == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Task> createTask(@PathVariable String slug, @PathVariable Long sprintId, @RequestBody Task task) {
        Task newTask = taskService.createTask(slug, sprintId, task);
        if (newTask == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

}
