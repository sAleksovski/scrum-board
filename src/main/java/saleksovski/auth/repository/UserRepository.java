package saleksovski.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import saleksovski.auth.model.MyUser;

import java.util.List;

/**
 * Created by stefan on 1/14/16.
 */
public interface UserRepository extends JpaRepository<MyUser, Long> {

    MyUser findByEmail(String email);

    @SuppressWarnings("JpaQlInspection")
    @Query("select m from MyUser m where lower(concat(m.firstName, ' ', m.lastName, ' ', m.firstName) ) like lower(concat('%',:query, '%') ) ")
    List<MyUser> findUsers(@Param("query") String query);
}