package saleksovski.scrum.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by stefan on 2/20/16.
 */
@Entity
@Table(name = "board")
public class Board extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", length = 100, nullable = false, unique = true)
    private String slug;

    @OneToMany
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "board")
    private Set<BoardUserRole> boardUserRole = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public Set<BoardUserRole> getBoardUserRole() {
        return boardUserRole;
    }

    public void setBoardUserRole(Set<BoardUserRole> boardUserRole) {
        this.boardUserRole = boardUserRole;
    }
}
