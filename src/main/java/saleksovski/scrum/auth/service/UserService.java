package saleksovski.scrum.auth.service;

import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import saleksovski.scrum.auth.model.User;

/**
 * Created by stefan on 1/14/16.
 */
public interface UserService {

    User registerNewUserAccount(UserProfile userAccountData, ConnectionKey socialMediaService);
}