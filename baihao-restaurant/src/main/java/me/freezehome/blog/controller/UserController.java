package me.freezehome.blog.controller;

import me.freezehome.blog.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import me.freezehome.blog.model.User;

import java.util.List;

/**
 * Created by freeze on 16-11-13.
 */
@Controller
@RequestMapping(value = "/login/*")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "checkin", method = RequestMethod.GET)
    @ResponseBody //返回字符串用
    public String getUserProfile(@RequestParam("userName") String userName,
                                 @RequestParam("userPassword") String userPassword){
        List<String> userNameList = userService.getAllUserName();
        if(!userNameList.contains(userName)) {
            return "false";
        }
        String password = userService.getUserById(userName).getUserPassword();
        if(password.equals(userPassword)) {
            //userService.updateUserLogin("Yes", userName);
            return "true";
        }
        else {
            return "false";
        }
    }
}
