package saleksovski.scrum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import saleksovski.auth.model.MyUser;

import javax.persistence.*;

/**
 * Created by stefan on 3/24/16.
 */
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "text", length = 2000)
    private String text;

    @ManyToOne
    private MyUser creator;

    @ManyToOne
    @JsonIgnore
    private Task task;

    @Override
    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MyUser getCreator() {
        return creator;
    }

    public void setCreator(MyUser creator) {
        this.creator = creator;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
