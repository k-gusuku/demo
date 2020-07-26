package com.example.demo.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CashRegisterController {

    @GetMapping("/cashRegister_contents")
    public String getCashRegister(Model model) {
        model.addAttribute("contents", "base/register/cashRegister::cashRegister_contents");
        return "base/homeLayout";
    }
}
