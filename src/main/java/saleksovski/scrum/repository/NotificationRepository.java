package saleksovski.scrum.repository;

import org.springframework.data.repository.CrudRepository;
import saleksovski.auth.model.MyUser;
import saleksovski.scrum.model.Notification;
import saleksovski.scrum.model.Task;
import saleksovski.scrum.model.enums.NotificationType;

import java.util.List;

/**
 * Created by stefan on 5/28/16.
 */
public interface NotificationRepository extends CrudRepository<Notification, Long> {

    List<Notification> findByUserOrderByIdDesc(MyUser user);

    List<Notification> findByUserAndNotificationTypeAndTask(MyUser user, NotificationType notificationType, Task task);

}
