package saleksovski.scrum.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by stefan on 1/14/16.
 */
@Configuration
@EnableJpaRepositories(basePackages = {
        "saleksovski.auth",
        "saleksovski.scrum"
})
@EntityScan(basePackages = {
        "saleksovski.auth.model",
        "saleksovski.scrum.model"
})
public class PersistenceContext {

}
