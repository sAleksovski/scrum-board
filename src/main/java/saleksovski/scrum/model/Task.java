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
    private MyUser createdBy;

    @ManyToOne
    private MyUser assignedTo;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 5000)
    private String description;

    @ManyToOne
    private Sprint sprint;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_progress", length = 20, nullable = false)
    private TaskProgress taskProgress;

    @Override
    public Long getId() {
        return id;
    }

    public MyUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(MyUser createdBy) {
        this.createdBy = createdBy;
    }

    public MyUser getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(MyUser assignedTo) {
        this.assignedTo = assignedTo;
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

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public TaskProgress getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(TaskProgress taskProgress) {
        this.taskProgress = taskProgress;
    }
}
