package saleksovski.scrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import saleksovski.auth.SecurityUtil;
import saleksovski.auth.exception.UserNotAuthenticated;
import saleksovski.auth.model.MyUser;
import saleksovski.auth.repository.UserRepository;
import saleksovski.scrum.service.utils.StringUtils;

import java.util.*;

/**
 * Created by stefan on 2/16/16.
 */
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UsersConnectionRepository usersConnectionRepository;

    @RequestMapping("/api/me")
    public
    @ResponseBody
    MyUser me() throws UserNotAuthenticated {
        return SecurityUtil.getUserDetails();
    }

    @RequestMapping("/api/users")
    public
    @ResponseBody
    Collection<MyUser> searchUsers(@RequestParam(value = "query") String query) {
        if (query == null) {
            return new ArrayList<>();
        }
        String transposedQuery = StringUtils.transpose(query);
        List<MyUser> a = userRepository.findUsers(query);
        List<MyUser> b = userRepository.findUsers(transposedQuery);
        Set<MyUser> all = new HashSet<>();
        all.addAll(a);
        all.addAll(b);
        return all;
    }

}
