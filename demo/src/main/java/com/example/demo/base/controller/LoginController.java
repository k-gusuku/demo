package com.example.demo.base.controller;

import org.springframework.ui.Model;

public interface LoginController {

    /**
     * ログイン画面遷移用getメソッド
     *
     * @param model model
     * @return ログイン画面へ遷移
     */
    public String getLogin(Model model);

    /**
     * ログイン画面遷移用postメソッド
     *
     * @param model model
     * @return ログイン画面へ遷移
     */
    public String postLogin(Model model);
}
