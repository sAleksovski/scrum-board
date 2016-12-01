package saleksovski.scrum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import saleksovski.auth.model.MyUser;
import saleksovski.scrum.model.*;
import saleksovski.scrum.model.enums.NotificationType;
import saleksovski.scrum.repository.NotificationRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<Notification> findByUser(MyUser user, int page, int size) {
        Pageable pageRequest = new PageRequest(page, size);
        return notificationRepository.findByUserOrderByIdDesc(user, pageRequest);
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
            Notification notification = new Notification();
            notification.getCreators().add(creator);
            notification.setUser(user);
            notification.setNotificationType(notificationType);
            notification.setUnread(true);
            notification.setBoard(board);
            notification.setSprint(sprint);
            notification.setTask(task);
            notification.setUrl(url);

            notification.getCreators().add(user);
            return notificationRepository.save(notification);
        }
    }

    public void markAsRead(MyUser user) {
        notificationRepository.markAsReadForUser(user);
    }

    public List<Notification> createAddCommentNotifications(Comment comment, String url, MyUser commentCreator) {
        Set<MyUser> affectedUsers = new HashSet<>();

        comment.getTask().getComments().forEach(c -> affectedUsers.add(c.getCreator()));
        affectedUsers.add(comment.getTask().getAssignedTo());
        affectedUsers.add(comment.getTask().getCreatedBy());
        affectedUsers.remove(commentCreator);

        return affectedUsers.stream()
                .map(u -> createNotification(
                        commentCreator,
                        u,
                        NotificationType.COMMENTED_ON_TASK,
                        null,
                        null,
                        comment.getTask(),
                        url
                ))
                .collect(Collectors.toList());
    }
}
