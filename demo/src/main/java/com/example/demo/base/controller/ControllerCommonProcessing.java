package com.example.demo.base.controller;

public class ControllerCommonProcessing {
    // 商品イメージ表示用に修正(パターン①)
    protected String productImageForDisplayPattern1(String productImageId) {
        String productImageForDisplay = "img/" + productImageId + ".png";
        return productImageForDisplay;
    }

    // 商品イメージ表示用に修正(パターン②)
    protected String productImageForDisplayPattern2(String productImageId) {
        String productImageForDisplay = "../img/" + productImageId + ".png";
        return productImageForDisplay;
    }
}
