package saleksovski.scrum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saleksovski.scrum.config.WebAppContext;

/**
 * Created by stefan on 1/21/16.
 */
@RestController
public class HomeController {

    @RequestMapping(value = "/")
    public String index() {
        return "<script type=\"text/javascript\">window.location = \"" + WebAppContext.getFrontendUrl() + "\";</script>";
    }

}
