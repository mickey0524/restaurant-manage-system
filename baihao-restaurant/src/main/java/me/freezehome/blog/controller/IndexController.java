package me.freezehome.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by freeze on 16-11-13.
 */
@Controller
public class IndexController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu() {
        return "menu";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "about";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.GET)
    public String contact() {
        return "contact";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/serve", method = RequestMethod.GET)
    public String serve() {
        return "serve";
    }

    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public String services() {
        return "services";
    }
}
