package com.firstWeb.controller;

import com.firstWeb.bean.response.UserInfo;
import com.firstWeb.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping({"/user"})
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LogManager.getLogger(RegisterController.class);

    @GetMapping(value = "/selectUserById/{id}")
    public String selectUserById(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("selectUserById: begin");
        UserInfo user = userService.selectUserById(id);
        return user.getEmail();
    }

    @RequestMapping(value = "/selectUserByConditions", method = RequestMethod.GET)
//    @ResponseBody
    public List<UserInfo> selectUserByConditions(String value, HttpServletRequest request, HttpServletResponse response) {
        List<UserInfo> user = userService.selectUserByConditions("zxc");
        return user;
    }
}
