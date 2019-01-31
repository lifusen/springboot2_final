package com.itfusen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lifusen on 2019/1/23 14:49
 */
@Controller
public class FtlController {

    @RequestMapping("/itfusen/ftlView")
    public String ftlView()
    {
        return "ftlView";
    }
}
