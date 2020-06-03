package com.example.demo.base.controller.impl;

import com.example.demo.base.controller.CashRegisterController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CashRegisterControllerImpl implements CashRegisterController {

    @GetMapping("/cashRegister_contents")
    public String getCashRegister(Model model) {
        model.addAttribute("contents", "base/cashRegister::cashRegister_contents");
        return "base/homeLayout";
    }
}
