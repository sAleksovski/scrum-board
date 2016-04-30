package saleksovski.scrum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import saleksovski.auth.model.MyUser;
import saleksovski.scrum.model.enums.UserRole;

import javax.persistence.*;

/**
 * Created by stefan on 2/20/16.
 */
@Entity
@Table(name = "board_user_role")
public class BoardUserRole extends BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Board board;

    @ManyToOne
    @JoinColumn(name = "bUser")
    private MyUser user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private UserRole role;

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

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
