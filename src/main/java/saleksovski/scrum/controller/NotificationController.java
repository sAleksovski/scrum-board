package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import saleksovski.auth.SecurityUtil;
import saleksovski.auth.exception.UserNotAuthenticated;
import saleksovski.scrum.model.Notification;
import saleksovski.scrum.service.NotificationService;

import java.util.List;

/**
 * Created by stefan on 5/28/16.
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping
    public List<Notification> list(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size)
            throws UserNotAuthenticated {

        return notificationService.findByUser(SecurityUtil.getUserDetails(), page, size);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> markAsRead() throws UserNotAuthenticated {
        notificationService.markAsRead(SecurityUtil.getUserDetails());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
