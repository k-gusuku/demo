package com.example.demo.base.controller.impl;

import com.example.demo.base.controller.HomeController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeControllerImpl implements HomeController {

    @GetMapping("/home")
    public String getHome(Model model) {

        model.addAttribute("contents", "base/home::home_contents");

        return "base/homeLayout";
    }

    @PostMapping("/home")
    public String postHome(Model model) {

        model.addAttribute("contents", "base/home::home_contents");

        return "base/homeLayout";
    }
}
