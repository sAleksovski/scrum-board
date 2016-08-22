package saleksovski.scrum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import saleksovski.auth.model.MyUser;
import saleksovski.scrum.model.Board;
import saleksovski.scrum.model.Notification;
import saleksovski.scrum.model.Sprint;
import saleksovski.scrum.model.Task;
import saleksovski.scrum.model.enums.NotificationType;
import saleksovski.scrum.repository.NotificationRepository;

import java.util.List;

/**
 * Created by stefan on 5/28/16.
 */
@Service
public class NotificationService {

    private NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> findByUser(MyUser user) {
        return notificationRepository.findByUserOrderByIdDesc(user);
    }

    public Notification createNotification(MyUser creator, MyUser user, NotificationType notificationType, Board board, Sprint sprint, Task task, String url) {
        if (notificationType == NotificationType.COMMENTED_ON_TASK) {
            List<Notification> oldNotifications = notificationRepository.findByUserAndNotificationTypeAndTask(user, notificationType, task);

            Notification notification;

            if (oldNotifications.size() == 0) {
                notification = new Notification();
                notification.getCreators().add(creator);
                notification.setUser(user);
                notification.setNotificationType(notificationType);
                notification.setUnread(true);
                notification.setBoard(board);
                notification.setSprint(sprint);
                notification.setTask(task);
                notification.setUrl(url);
            } else {
                notification = oldNotifications.get(0);
                notification.getCreators().add(creator);
                notification.setUnread(true);
            }
            return notificationRepository.save(notification);
        } else {
            Notification notification = null;
            notification = new Notification();
            notification.getCreators().add(creator);
            notification.setUser(user);
            notification.setNotificationType(notificationType);
            notification.setUnread(true);
            notification.setBoard(board);
            notification.setSprint(sprint);
            notification.setTask(task);
            notification.setUrl(url);

            notification.setUnread(true);
            notification.getCreators().add(user);
            return notificationRepository.save(notification);
        }
    }

}
