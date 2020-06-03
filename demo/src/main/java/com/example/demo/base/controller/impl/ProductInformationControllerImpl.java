package com.example.demo.base.controller.impl;

import com.example.demo.base.controller.ProductInformationController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductInformationControllerImpl implements ProductInformationController {

    @GetMapping("/productInformation_contents")
    public String getProductInformation(Model model) {

        model.addAttribute("contents", "base/productInformation::productInformation_contents");

        return "base/homeLayout";
    }
}
