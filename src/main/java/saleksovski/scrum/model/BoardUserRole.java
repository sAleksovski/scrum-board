package saleksovski.scrum.model;

import saleksovski.scrum.auth.model.MyUser;
import saleksovski.scrum.enums.UserRole;

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
    MyUser user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    UserRole role;

    @Override
    public Long getId() {
        return id;
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
