package com.example.demo.base.controller;

import org.springframework.ui.Model;

public interface CashRegisterController {

    /**
     * レジ画面遷移用getメソッド
     *
     * @param model model
     * @return レジ画面へ遷移
     */
    public String getCashRegister(Model model);
}
