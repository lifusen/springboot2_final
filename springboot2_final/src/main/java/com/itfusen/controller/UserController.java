package com.itfusen.controller;

import com.itfusen.domain.User;
import com.itfusen.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lifusen on 2019/1/24 14:15
 */
@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @RequestMapping("/itfusen/insertUser")
    public String insertUser(User user)
    {
        return userService.insertUser(user)>0?"INSERT SUCCESS":"INSERT FAILED";
    }

    @RequestMapping("/itfusen/selectAllUser")
    public Object selectAllUser()
    {
        return userService.selectAllUser();
    }

    @RequestMapping("/itfusen/selectPageUser")
    public Object selectPageUser(int page,int count)
    {
        return userService.selectPageUser(page,count);
    }

    public String csonline()
    {
        return "AaAAAASDASDASsdDSDSD";
    }
}
