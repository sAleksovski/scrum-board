package saleksovski.scrum.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saleksovski.scrum.auth.model.User;

/**
 * Created by stefan on 1/14/16.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}