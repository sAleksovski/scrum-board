package saleksovski.scrum.auth.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saleksovski.scrum.auth.enums.SocialMediaService;
import saleksovski.scrum.auth.model.User;
import saleksovski.scrum.auth.service.UserService;

/**
 * Created by stefan on 1/14/16.
 */
@Service
public class RepositoryUserService implements UserService {

    private UserRepository repository;

    @Autowired
    public RepositoryUserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(UserProfile userAccountData, ConnectionKey connectionKey) {

        User.Builder user = User.getBuilder()
                .email(userAccountData.getEmail())
                .firstName(userAccountData.getFirstName())
                .lastName(userAccountData.getLastName())
                .password(null);

        user.signInProvider(SocialMediaService.valueOf(connectionKey.getProviderId().toUpperCase()));

        User registered = user.build();

        if (registered.getEmail() == null) {
            registered.setEmail(connectionKey.getProviderUserId() + "@" + connectionKey.getProviderId() + ".com");
        }

        if (!emailExist(registered.getEmail())) {
            return repository.save(registered);
        } else {
            return repository.findByEmail(registered.getEmail());
        }
    }

    private boolean emailExist(String email) {
        User user = repository.findByEmail(email);

        return user != null;
    }

}
