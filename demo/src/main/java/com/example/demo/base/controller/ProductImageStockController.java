package com.example.demo.base.controller;

import com.example.demo.base.conversion.productimagestock.ProductImageStockConversion;
import com.example.demo.base.dao.productimagestock.ProductImageStockDto;
import com.example.demo.base.domain.productimagestock.ProductImageStockForm;
import com.example.demo.base.domain.productimagestock.ProductImageStockGroupOrder;
import com.example.demo.base.service.ProductImageStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProductImageStockController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 未入力のStringをnullに設定する
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    private final ProductImageStockService productImageStockService;
    private final ProductImageStockConversion productImageStockConversion;

    @Autowired
    public ProductImageStockController(ProductImageStockService productImageStockService, ProductImageStockConversion productImageStockConversion) {
        this.productImageStockService = productImageStockService;
        this.productImageStockConversion = productImageStockConversion;
    }

    // 商品イメージ種類用MAP
    private Map<String, String> radioProductImageType;
    // 検索用商品イメージ種類用MAP
    private Map<String, String> radioProductImageTypeForSearch;

    // 商品イメージの種類ラジオボタンの初期化メソッド
    private Map<String, String> initRadioProductImageType() {

        Map<String, String> radio = new LinkedHashMap<>();

        radio.put("ゲームソフト", "ゲームソフト");
        radio.put("本", "本");
        radio.put("DVD", "DVD");
        radio.put("その他", "その他");

        return radio;
    }

    // 検索用商品イメージの種類ラジオボタンの初期化メソッド
    private Map<String, String> initRadioProductImageTypeForSearch() {

        Map<String, String> radio = new LinkedHashMap<>();

        radio.put("ゲームソフト", "ゲームソフト");
        radio.put("本", "本");
        radio.put("DVD", "DVD");
        radio.put("その他", "その他");
        radio.put("指定なし", null);

        return radio;
    }

    // 商品イメージ表示用に修正(パターン①)
    private String productImageForDisplayPattern1(String productImageId) {
        String productImageForDisplay = "img/" + productImageId + ".png";
        return productImageForDisplay;
    }

    // 商品イメージ表示用に修正(パターン②)
    private String productImageForDisplayPattern2(String productImageId) {
        String productImageForDisplay = "../img/" + productImageId + ".png";
        return productImageForDisplay;
    }

    // 商品イメージ情報のGETメソッド
    @GetMapping("/productImageStock_contents")
    public String getProductImageStock(@ModelAttribute ProductImageStockForm productImageStockForm, Model model) {
        radioProductImageTypeForSearch = initRadioProductImageTypeForSearch();

        List<ProductImageStockForm> productImageStockFormList = productImageStockService.selectMany(productImageStockForm.getProductImageId(), productImageStockForm.getProductImageName(), productImageStockForm.getProductImageType()).stream().map(p -> {
            p.setProductImage(productImageForDisplayPattern1(p.getProductImageId()));
            return productImageStockConversion.dto2Form(p);
        }).collect(Collectors.toList());

        model.addAttribute("contents", "base/productimagestock/productImageStock::productImageStock_contents");
        model.addAttribute("radioProductImageTypeForSearch", radioProductImageTypeForSearch);
        model.addAttribute("productImageStockFormList", productImageStockFormList);

        return "base/homeLayout";
    }

    // 商品画像詳細画面のGETメソッド
    @GetMapping("/productImageStockDetail/{id:.+}")
    public String getProductImageStockDetail(@ModelAttribute ProductImageStockForm productImageStockForm, Model model, @PathVariable("id") String productImageId) {

        System.out.println("productImageId =" + productImageId);

        if (productImageId != null && productImageId.length() > 0) {
            radioProductImageType = initRadioProductImageType();
            productImageStockForm = productImageStockConversion.dto2Form(productImageStockService.selectOne(productImageId));

            String productImage = productImageForDisplayPattern2(productImageStockForm.getProductImageId());

            model.addAttribute("productImageStockForm", productImageStockForm);
            model.addAttribute("productImage", productImage);
            model.addAttribute("radioProductImageType", radioProductImageType);
            model.addAttribute("contents", "base/productimagestock/productImageStockDetail::productImageStockDetail_contents");
        }

        return "base/homeLayout";
    }

    // 更新用のpostメソッド
    @PostMapping(value = "/productImageStockDetail", params = "update")
    public String postProductImageStockDetailUpdate(@ModelAttribute @Validated(ProductImageStockGroupOrder.class) ProductImageStockForm productImageStockForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            radioProductImageType = initRadioProductImageType();
            String productImage = productImageForDisplayPattern2(productImageStockForm.getProductImageId());

            model.addAttribute("productImage", productImage);
            model.addAttribute("radioProductImageType", radioProductImageType);
            model.addAttribute("contents", "base/productimagestock/productImageStockDetail::productImageStockDetail_contents");

            return "base/homeLayout";
        }
        System.out.println("更新ボタンの処理");

        ProductImageStockDto productImageStockDto = productImageStockConversion.form2Dto(productImageStockForm);
        boolean result = productImageStockService.updateOne(productImageStockDto);
        if (result) {
            model.addAttribute("result", "更新成功");
        } else {
            model.addAttribute("result", "更新失敗");
        }

        return getProductImageStockDetail(productImageStockForm, model, productImageStockForm.getProductImageId());
    }

    // 商品画像情報削除画面のPOSTメソッド
    @PostMapping(value = "/productImageStockDetail", params = "delete")
    public String postProductImageStockDelete(@ModelAttribute ProductImageStockForm productImageStockForm, Model model) {
        System.out.println("削除ボタンの処理");

        boolean result = productImageStockService.deleteOne(productImageStockForm.getProductImageId());

        if (result) {
            model.addAttribute("result", "削除成功");
        } else {
            model.addAttribute("result", "削除失敗");
        }

        // 商品イメージ画面の検索結果用に商品イメージIDと商品イメージ名と商品イメージの種類を空にする
        productImageStockForm.setProductImageId(null);
        productImageStockForm.setProductImageName(null);
        productImageStockForm.setProductImageType(null);

        return getProductImageStock(productImageStockForm, model);
    }

    // 新規商品イメージ登録のGETメソッド
    @GetMapping("/newProductImageStockRegistration_contents")
    public String getNewProductImageStockRegistration(@ModelAttribute ProductImageStockForm productImageStockForm, Model model) {

        radioProductImageType = initRadioProductImageType();

        model.addAttribute("radioProductImageType", radioProductImageType);
        model.addAttribute("contents", "base/productimagestock/newProductImageStockRegistration::newProductImageStockRegistration_contents");
        return "base/homeLayout";
    }

    // 新規商品イメージ登録のPOSTメソッド
    @PostMapping("/newProductImageStockRegistration_contents")
    public String postNewProductImageStockRegistration(@ModelAttribute @Validated(ProductImageStockGroupOrder.class) ProductImageStockForm productImageStockForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getNewProductImageStockRegistration(productImageStockForm, model);
        }
        System.out.println(productImageStockForm);

        ProductImageStockDto productImageStockDto = productImageStockConversion.form2Dto(productImageStockForm);
        boolean result = productImageStockService.insertOne(productImageStockDto);
        //会員イメージ登録結果の判定
        if (result) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }

        // 商品イメージ画面の検索結果用に商品イメージIDと商品イメージ名と商品イメージの種類を空にする
        productImageStockForm.setProductImageId(null);
        productImageStockForm.setProductImageName(null);
        productImageStockForm.setProductImageType(null);

        return getProductImageStock(productImageStockForm, model);
    }

    // 商品一覧のCSV出力処理
    @GetMapping("/productImageStockList/csv")
    public ResponseEntity<byte[]> getProductImageStockListCsv(Model model) {
        // 商品を全件取得して、CSVをサーバーに保存する
        productImageStockService.productCsvOut();

        byte[] bytes = null;

        try {
            bytes = productImageStockService.getFile("productImageStock.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "productImageStock.csv");

        // product.csvを戻す
        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
}
