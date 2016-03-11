package saleksovski.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.social.twitter.api.Twitter;
import saleksovski.auth.model.enums.SocialMediaService;
import saleksovski.auth.exception.UserNotAuthenticated;
import saleksovski.auth.model.MyUser;
import saleksovski.auth.repository.UserRepository;

/**
 * Created by stefan on 1/21/16.
 */
public class SecurityUtil {

    public static void logInUser(MyUser user) {
        MyUser userDetails = MyUser.getBuilder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(user.getRole())
                .socialSignInProvider(user.getSocialSignInProvider())
                .username(user.getEmail())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static MyUser getUserDetails() throws UserNotAuthenticated {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof MyUser) {
            return (MyUser) principal;
        } else {
            throw new UserNotAuthenticated();
        }
    }

    public static Connection getUserConnection(UserRepository userRepository, UsersConnectionRepository usersConnectionRepository, String userId) {
        ConnectionRepository repository = usersConnectionRepository.createConnectionRepository(userId);

        Connection connection = null;

        MyUser u = userRepository.findByEmail(userId);

        if (u.getSocialSignInProvider() == SocialMediaService.FACEBOOK) {
            connection = repository.findPrimaryConnection(Facebook.class);
        } else if (u.getSocialSignInProvider() == SocialMediaService.GOOGLE) {
            connection = repository.findPrimaryConnection(Google.class);
        } else if (u.getSocialSignInProvider() == SocialMediaService.TWITTER) {
            connection = repository.findPrimaryConnection(Twitter.class);
        }

        return connection;
    }
}
