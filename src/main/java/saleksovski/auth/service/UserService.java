package saleksovski.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import saleksovski.auth.model.enums.Role;
import saleksovski.auth.model.enums.SocialMediaService;
import saleksovski.auth.model.MyUser;
import saleksovski.auth.repository.UserRepository;

/**
 * Created by stefan on 1/14/16.
 */
@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MyUser registerNewUserAccount(Connection<?> connection) {// UserProfile userAccountData, ConnectionKey connectionKey) {

        UserProfile userAccountData = connection.fetchUserProfile();
        ConnectionKey connectionKey = connection.getKey();

        String email = userAccountData.getEmail();

        if (email == null) {
            email = connectionKey.getProviderUserId() + "@" + connectionKey.getProviderId() + ".com";
        }

        MyUser.Builder user = MyUser.getBuilder()
                .username(email)
                .email(email)
                .firstName(userAccountData.getFirstName())
                .lastName(userAccountData.getLastName())
                .password("SocialPassword")
                .role(Role.ROLE_USER)
                .imageUrl(connection.getImageUrl());

        user.socialSignInProvider(SocialMediaService.valueOf(connectionKey.getProviderId().toUpperCase()));

        MyUser registered = user.build();

        if (!emailExist(registered.getEmail())) {
            return repository.save(registered);
        } else {
            return repository.findByEmail(registered.getEmail());
        }
    }

    private boolean emailExist(String email) {
        MyUser user = repository.findByEmail(email);

        return user != null;
    }

}
