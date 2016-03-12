package saleksovski.scrum.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "board")
    private List<BoardUserRole> boardUserRole = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Sprint> sprints = new ArrayList<>();

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

    public List<BoardUserRole> getBoardUserRole() {
        return boardUserRole;
    }

    public void setBoardUserRole(List<BoardUserRole> boardUserRole) {
        this.boardUserRole = boardUserRole;
    }

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }
}
