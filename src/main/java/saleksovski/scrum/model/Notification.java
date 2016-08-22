package saleksovski.scrum.model;

import saleksovski.auth.model.MyUser;
import saleksovski.scrum.model.enums.NotificationType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan on 5/28/16.
 */
@Entity
public class Notification extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "notificationType", length = 20, nullable = false)
    private NotificationType notificationType;

    @ManyToMany()
    private List<MyUser> creators = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "nUser")
    private MyUser user;

    @Column
    private boolean unread;

    @ManyToOne
    private Board board;

    @ManyToOne
    private Sprint sprint;

    @ManyToOne
    private Task task;

    @Column
    private String url;

    @Override
    public Long getId() {
        return id;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public List<MyUser> getCreators() {
        return creators;
    }

    public void setCreators(List<MyUser> creators) {
        this.creators = creators;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public boolean getUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
