package com.example.demo.base.controller.impl;

import com.example.demo.base.controller.LoginController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginControllerImpl implements LoginController {

    @GetMapping("/login")
    public String getLogin(Model model) {

        //login.htmlに画面遷移
        return "base/login";
    }

    @PostMapping("/login")
    public String postLogin(Model model) {

        //login.htmlに画面遷移
        return "base/login";
    }
}
