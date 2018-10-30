package com.imooc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author erpljq
 * @date 2018/10/8
 */
@Controller
@RequestMapping("/")
public class indexController {

    @GetMapping("/")
    public String index(){
        return "center/center";
    }
}
