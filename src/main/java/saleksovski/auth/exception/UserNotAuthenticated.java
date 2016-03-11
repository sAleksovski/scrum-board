package saleksovski.auth.exception;

/**
 * Created by stefan on 1/25/16.
 */
public class UserNotAuthenticated extends Exception {

    public UserNotAuthenticated() {
        super("User not authenticated");
    }

}
