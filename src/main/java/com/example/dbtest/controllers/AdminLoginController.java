package com.example.dbtest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * ログイン画面
 */
@Controller
public class AdminLoginController {

    @GetMapping("/admin/login") // Anotationのvalueは省略可
    public String login() {
        return "admin-login";
    }

    @GetMapping("/admin-access-denied")
    public ModelAndView showAccessDenied(ModelAndView mav) {

        mav.addObject("title", "Access-Denied");
        mav.setViewName("access-denied");
        return mav;

    }

}
