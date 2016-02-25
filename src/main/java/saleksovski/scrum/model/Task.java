package saleksovski.scrum.model;

import saleksovski.scrum.auth.model.MyUser;
import saleksovski.scrum.enums.TaskProgress;

import javax.persistence.*;

/**
 * Created by stefan on 2/20/16.
 */
@Entity
@Table(name = "task")
public class Task extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Board board;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_progress", length = 20, nullable = false)
    private TaskProgress taskProgress;

    @ManyToOne
    private MyUser user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Override
    public Long getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public TaskProgress getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(TaskProgress taskProgress) {
        this.taskProgress = taskProgress;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
