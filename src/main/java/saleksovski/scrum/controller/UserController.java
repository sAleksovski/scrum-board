package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import saleksovski.scrum.auth.SecurityUtil;
import saleksovski.scrum.auth.exception.UserNotAuthenticated;
import saleksovski.scrum.auth.model.MyUser;
import saleksovski.scrum.auth.repository.UserRepository;

/**
 * Created by stefan on 2/16/16.
 */
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UsersConnectionRepository usersConnectionRepository;

    @RequestMapping(value = "/api/user")
    public
    @ResponseBody
    MyUser test() {
        String userId;
        try {
            userId = SecurityUtil.getUserDetails().getUserId();
        } catch (UserNotAuthenticated userNotAuthenticated) {
            userId = null;
        }
        if (userId != null) {
            return userRepository.findByEmail(userId);
        }

        return null;
    }

}
