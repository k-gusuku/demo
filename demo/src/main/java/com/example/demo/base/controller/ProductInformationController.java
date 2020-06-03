package com.example.demo.base.controller;

import org.springframework.ui.Model;

public interface ProductInformationController {

    /**
     * 商品情報画面遷移用getメソッド
     *
     * @param model model
     * @return 商品情報画面へ遷移
     */
    public String getProductInformation(Model model);
}
