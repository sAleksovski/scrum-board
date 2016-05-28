package saleksovski.scrum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by stefan on 5/18/16.
 */
@Service
public class WebSocketService {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebSocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendNotification(String username, Object notificationBody) {
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/notifications", notificationBody);
    }

}
