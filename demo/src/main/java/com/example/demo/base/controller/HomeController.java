package com.example.demo.base.controller;

import org.springframework.ui.Model;

public interface HomeController {

    /**
     *ホーム画面遷移用getメソッド
     *
     * @param model model
     * @return ホーム画面へ遷移
     */
    public String getHome(Model model);

    /**
     *ホーム画面遷移用postメソッド
     *
     * @param model model
     * @return ホーム画面へ遷移
     */
    public String postHome(Model model);
}
