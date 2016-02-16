package saleksovski.scrum.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import saleksovski.scrum.auth.model.MyUserDetails;
import saleksovski.scrum.auth.model.User;
import saleksovski.scrum.auth.repository.UserRepository;

/**
 * Created by stefan on 1/14/16.
 */
public class RepositoryUserDetailsService implements UserDetailsService {

    private UserRepository repository;

    @Autowired
    public RepositoryUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        return MyUserDetails.getBuilder()
                .firstName(user.getFirstName())
                .id(user.getId())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(user.getRole())
                .socialSignInProvider(user.getSignInProvider())
                .username(user.getEmail())
                .build();
    }
}
