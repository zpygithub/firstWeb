package com.firstWeb.controller;

import com.firstWeb.model.User;
import com.firstWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zpy on 2017/2/5.
 */
@RestController
@RequestMapping({"/user"})
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/selectUserById", method = RequestMethod.GET)
    public String selectUserById(String id, HttpServletRequest request, HttpServletResponse response) {
        User user = userService.selectUserById("1");
        return user.getEmail();
    }

    @RequestMapping(value = "/selectUserByConditions", method = RequestMethod.GET)
//    @ResponseBody
    public List<User> selectUserByConditions(String value, HttpServletRequest request, HttpServletResponse response) {
        List<User> user = userService.selectUserByConditions("zxc");
        return user;
    }
}
