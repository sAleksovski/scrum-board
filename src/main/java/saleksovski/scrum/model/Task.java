package saleksovski.scrum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import saleksovski.auth.model.MyUser;
import saleksovski.scrum.model.enums.TaskDifficulty;
import saleksovski.scrum.model.enums.TaskPriority;
import saleksovski.scrum.model.enums.TaskProgress;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    @JsonIgnore
    private Sprint sprint;

    @Enumerated(EnumType.STRING)
    @Column(name = "progress", length = 20, nullable = false)
    private TaskProgress progress;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", length = 5)
    private TaskDifficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", length = 10)
    private TaskPriority priority;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "position")
    private double position;

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

    public TaskProgress getProgress() {
        return progress;
    }

    public void setProgress(TaskProgress progress) {
        this.progress = progress;
    }

    public TaskDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(TaskDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }
}
