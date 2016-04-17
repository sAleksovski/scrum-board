package saleksovski.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import saleksovski.auth.exception.UserNotAuthenticated;
import saleksovski.auth.model.MyUser;
import saleksovski.auth.repository.UserRepository;

import javax.annotation.PostConstruct;

/**
 * Created by stefan on 1/21/16.
 */
@Component
public class SecurityUtil {

    private static UserRepository userRepository;

    @Autowired
    private UserRepository ur;

    public static void logInUser(MyUser user) {
        MyUser userDetails = MyUser.getBuilder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(user.getRole())
                .socialSignInProvider(user.getSocialSignInProvider())
                .username(user.getEmail())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static MyUser getUserDetails() throws UserNotAuthenticated {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof MyUser) {
            MyUser user = (MyUser) principal;

            String userId = user.getUserId();
            return userRepository.findByEmail(userId);
        } else {
            throw new UserNotAuthenticated();
        }
    }

    @PostConstruct
    public void init() {
        userRepository = ur;
    }

}
