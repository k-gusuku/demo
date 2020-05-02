package com.example.demo.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.Past;

@Controller
public class MainController {

    @GetMapping("/main")
    public String getMain(Model model){
        return "login/main";
    }

    @PostMapping("/main")
    public String postMain(Model model){
        return "redirect:/main";
    }
}
