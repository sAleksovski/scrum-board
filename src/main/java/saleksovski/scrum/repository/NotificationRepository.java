package saleksovski.scrum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import saleksovski.auth.model.MyUser;
import saleksovski.scrum.model.Notification;
import saleksovski.scrum.model.Task;
import saleksovski.scrum.model.enums.NotificationType;

import java.util.List;

/**
 * Created by stefan on 5/28/16.
 */
public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

    List<Notification> findByUserOrderByIdDesc(MyUser user, Pageable pageRequest);

    List<Notification> findByUserAndNotificationTypeAndTask(MyUser user, NotificationType notificationType, Task task);

    @SuppressWarnings("JpaQlInspection")
    @Modifying
    @Transactional
    @Query(value = "update Notification n set n.unread = false where n.user = ?1")
    void markAsReadForUser(MyUser user);
}
