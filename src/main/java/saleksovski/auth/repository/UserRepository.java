package saleksovski.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import saleksovski.auth.model.MyUser;

/**
 * Created by stefan on 1/14/16.
 */
public interface UserRepository extends JpaRepository<MyUser, Long> {

    MyUser findByEmail(String email);
}