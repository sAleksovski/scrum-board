package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<Notification> list() throws UserNotAuthenticated {
        return notificationService.findByUser(SecurityUtil.getUserDetails());
    }

}
