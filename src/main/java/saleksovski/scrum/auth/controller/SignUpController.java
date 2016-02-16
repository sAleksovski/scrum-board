package saleksovski.scrum.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.*;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import saleksovski.scrum.auth.SecurityUtil;
import saleksovski.scrum.auth.model.User;
import saleksovski.scrum.auth.repository.UserRepository;
import saleksovski.scrum.auth.service.UserService;

/**
 * Created by stefan on 1/14/16.
 */
@Controller
public class SignUpController {

    UserRepository userRepository;
    ProviderSignInUtils providerSignInUtils;

    @Autowired
    private UserService service;

    @Autowired
    public SignUpController(UserRepository userRepository,
                            ConnectionFactoryLocator connectionFactoryLocator,
                            UsersConnectionRepository connectionRepository) {
        this.userRepository = userRepository;
        this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
    }

    @RequestMapping(value = "/signup")
    public String redirectRequestToRegistrationPage(WebRequest request) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        if (connection != null) {
            UserProfile userProfile = connection.fetchUserProfile();
            ConnectionKey providerKey = connection.getKey();
            User registered = createUserAccount(userProfile, providerKey);
            SecurityUtil.logInUser(registered);
            providerSignInUtils.doPostSignUp(registered.getEmail(), request);
        }
        return "redirect:http://localhost:8000/";
    }

    private User createUserAccount(UserProfile userAccountData, ConnectionKey providerKey) {
        return service.registerNewUserAccount(userAccountData, providerKey);
    }

}