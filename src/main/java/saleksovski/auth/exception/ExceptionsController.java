package saleksovski.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import saleksovski.auth.exception.UserNotAuthenticated;

/**
 * Created by stefan on 4/17/16.
 */
@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(value = UserNotAuthenticated.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User Not Authenticated")
    public void userNotAuthenticated() {

    }

}
