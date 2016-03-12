package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saleksovski.scrum.model.Sprint;
import saleksovski.scrum.service.SprintService;

import java.util.List;

/**
 * Created by stefan on 2/25/16.
 */
@RestController
@RequestMapping(value = "/api/boards/{slug}/sprints")
public class SprintController {

    @Autowired
    SprintService sprintService;

    @RequestMapping
    public ResponseEntity<List<Sprint>> index(@PathVariable String slug) {
        List<Sprint> sprints = sprintService.findByBoardSlug(slug);
        if (sprints == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sprints, HttpStatus.OK);
    }

    @RequestMapping(value = "/{sprintId}")
    public ResponseEntity<Sprint> getSprint(@PathVariable String slug, @PathVariable Long sprintId) {
        Sprint sprint = sprintService.findByBoardSlugSprintId(slug, sprintId);
        if (sprint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(sprint, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Sprint> createSprint(@PathVariable String slug, @RequestBody Sprint sprint) {
        Sprint newSprint = sprintService.createSprint(slug, sprint);
        if (newSprint == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newSprint, HttpStatus.CREATED);
    }

}
