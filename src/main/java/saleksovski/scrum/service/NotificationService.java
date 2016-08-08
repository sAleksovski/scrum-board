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
        return notificationRepository.findByUser(user);
    }

    public Notification createNotification(MyUser creator, MyUser user, NotificationType notificationType, Board board, Sprint sprint, Task task) {
        List<Notification> oldNotifications = notificationRepository.findByUserAndNotificationType(user, notificationType);

        Notification notification = null;

        if (oldNotifications.size() == 0) {
            notification = new Notification();
            notification.getCreators().add(creator);
            notification.setUser(user);
            notification.setNotificationType(notificationType);
            notification.setUnread(true);
            notification.setBoard(board);
            notification.setSprint(sprint);
            notification.setTask(task);

            return notificationRepository.save(notification);
        }

        if (oldNotifications.size() > 1) {
            for (Notification n :
                    oldNotifications) {

                if (n.getBoard() != null && board != null && n.getBoard().getId() == board.getId()) {
                    notification = n;
                }

                if (n.getSprint() != null && sprint != null && n.getSprint().getId() == sprint.getId()) {
                    notification = n;
                }

                if (n.getTask() != null && task != null && n.getTask().getId() != null) {
                    notification = n;
                }
            }
        } else {
            notification = oldNotifications.get(0);
        }

        if (notification != null) {
            notification.setUnread(true);
            notification.getCreators().add(user);
            return notificationRepository.save(notification);
        }

        return null;
    }

}
