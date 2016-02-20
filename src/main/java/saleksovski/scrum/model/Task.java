package saleksovski.scrum.model;

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
    Board board;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_progress", length = 20, nullable = false)
    TaskProgress taskProgress;

    @Override
    public Long getId() {
        return id;
    }
}
