package saleksovski.scrum.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by stefan on 1/21/16.
 */
@RestController
public class HomeController {

    @RequestMapping(value = "/")
    public String index() throws IOException {
        return "<script type=\"text/javascript\">window.location = \"http://localhost:8000/\";</script>";
    }

}
