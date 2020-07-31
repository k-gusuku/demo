package com.example.demo.base.controller;

import com.example.demo.base.dao.productInformation.ProductInformationDto;
import com.example.demo.base.domain.productInformation.ProductForm;
import com.example.demo.base.service.impl.ProductInformationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class ProductInformationController {

    @Autowired
    ProductInformationServiceImpl productInformationServiceImpl;

    @GetMapping("/productInformation_contents")
    public String getProductInformation(@ModelAttribute ProductForm productForm, Model model) {

        model.addAttribute("contents", "base/product/productInformation::productInformation_contents");

        List<ProductInformationDto> productInformationDtoList = productInformationServiceImpl.selectMany(productForm.getProductId(), productForm.getProductName());

        model.addAttribute("productInformationDtoList", productInformationDtoList);

        return "base/homeLayout";
    }
}
