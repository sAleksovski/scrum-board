package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        if (tasks != null) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
