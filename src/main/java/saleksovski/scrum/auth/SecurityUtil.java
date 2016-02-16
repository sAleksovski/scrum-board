package saleksovski.scrum.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import saleksovski.scrum.auth.exception.UserNotAuthenticated;
import saleksovski.scrum.auth.model.MyUserDetails;
import saleksovski.scrum.auth.model.User;

/**
 * Created by stefan on 1/21/16.
 */
public class SecurityUtil {

    public static void logInUser(User user) {
        MyUserDetails userDetails = MyUserDetails.getBuilder()
                .firstName(user.getFirstName())
                .id(user.getId())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(user.getRole())
                .socialSignInProvider(user.getSignInProvider())
                .username(user.getEmail())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static MyUserDetails getUserDetails() throws UserNotAuthenticated {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof MyUserDetails) {
            return (MyUserDetails) principal;
        } else {
            throw new UserNotAuthenticated();
        }
    }
}
