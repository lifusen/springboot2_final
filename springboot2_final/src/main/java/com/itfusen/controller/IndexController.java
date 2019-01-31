package com.itfusen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lifusen on 2019/1/23 15:35
 */
@RestController
public class IndexController {

    @RequestMapping("/itfusen/indexString")
    public String indexString()
    {
        return "返回的字符串";
    }
}
