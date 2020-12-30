package com.example.demo.base.controller;

import com.example.demo.base.conversion.productimagestock.ProductImageStockConversion;
import com.example.demo.base.conversion.product.ProductConversion;
import com.example.demo.base.domain.productimagestock.ProductImageStockForm;
import com.example.demo.base.domain.product.ProductForm;
import com.example.demo.base.domain.product.ProductGroupOrder;
import com.example.demo.base.service.ProductImageStockService;
import com.example.demo.base.service.ProductService;
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
public class ProductController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 未入力のStringをnullに設定する
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    private final ProductService productService;
    private final ProductImageStockService productImageStockService;
    private final ProductConversion productConversion;
    @Autowired
    private final ProductImageStockConversion productImageStockConversion;

    @Autowired
    public ProductController(ProductService productService, ProductImageStockService productImageStockService, ProductConversion productConversion, ProductImageStockConversion productImageStockConversion) {
        this.productService = productService;
        this.productImageStockService = productImageStockService;
        this.productConversion = productConversion;
        this.productImageStockConversion = productImageStockConversion;
    }

    // 商品の種類用MAP
    private Map<String, String> radioProductType;
    // 商品イメージ種類用MAP
    private Map<String, String> radioProductImageType;

    // 商品の種類ラジオボタンの初期化メソッド
    private Map<String, String> initRadioProductType() {

        Map<String, String> radio = new LinkedHashMap<>();

        radio.put("ゲームソフト", "ゲームソフト");
        radio.put("本", "本");
        radio.put("DVD", "DVD");
        radio.put("その他", "その他");

        return radio;
    }

    // 商品イメージの種類ラジオボタンの初期化メソッド
    private Map<String, String> initRadioProductImageType() {

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

    // 商品情報のGETメソッド
    @GetMapping("/product_contents")
    public String getProduct(@ModelAttribute ProductForm productForm, Model model) {
        List<ProductForm> productFormList = productService.selectMany(productForm.getProductId(), productForm.getProductName()).stream().map(p -> {
            p.setProductImageId(productImageForDisplayPattern1(p.getProductImageId()));
            return productConversion.dto2Form(p);
        }).collect(Collectors.toList());

        model.addAttribute("contents", "base/product/product::product_contents");
        model.addAttribute("productFormList", productFormList);

        return "base/homeLayout";
    }

    // 商品詳細画面のGETメソッド
    @GetMapping("/productDetail/{id:.+}")
    public String getProductDetail(@ModelAttribute ProductForm productForm, Model model, @PathVariable("id") String productId) {
        System.out.println("productId =" + productId);
        radioProductImageType = initRadioProductImageType();

        // 商品イメージ検索用データの一時避難
        String searchForProductImageId = productForm.getSearchForProductImageId();
        String searchForProductImageName = productForm.getSearchForProductImageName();
        String searchForProductImageType = productForm.getSearchForProductImageType();

        List<ProductImageStockForm> productImageStockFormList = productImageStockService.selectMany(productForm.getSearchForProductImageId(), productForm.getSearchForProductImageName(), productForm.getSearchForProductImageType()).stream().map(p -> {
            p.setProductImage(productImageForDisplayPattern2(p.getProductImageId()));
            return productImageStockConversion.dto2Form(p);
        }).collect(Collectors.toList());

        model.addAttribute("productImageStockFormList", productImageStockFormList);
        model.addAttribute("radioProductImageType", radioProductImageType);

        if (productId != null && productId.length() > 0) {
            radioProductType = initRadioProductType();
            productForm = productConversion.dto2Form(productService.selectOne(productId));
            String imageForProductDetails = productImageForDisplayPattern2(productForm.getProductImageId());

            // 商品イメージ検索用データを格納
            productForm.setSearchForProductImageId(searchForProductImageId);
            productForm.setSearchForProductImageName(searchForProductImageName);
            productForm.setSearchForProductImageType(searchForProductImageType);

            model.addAttribute("contents", "base/product/productDetail::productDetail_contents");
            model.addAttribute("imageForProductDetails", imageForProductDetails);
            model.addAttribute("productForm", productForm);
            model.addAttribute("radioProductType", radioProductType);
        }
        return "base/homeLayout";
    }

    // 画像在庫検索用のpostメソッド
    @PostMapping(value = "/productDetail", params = "search")
    public String postProductImageStockSearchForProductDetail(@ModelAttribute ProductForm productForm, Model model) {
        System.out.println("商品ジメージ画像在庫検索");

        return getProductDetail(productForm, model, productForm.getProductId());
    }

    // 更新用のpostメソッド
    @PostMapping(value = "/productDetail", params = "update")
    public String postProductDetailUpdate(@ModelAttribute @Validated(ProductGroupOrder.class) ProductForm productForm, BindingResult bindingResult, Model model, String searchForProductImageId, String searchForProductImageName, String searchForProductImageType) {
        if (bindingResult.hasErrors()) {
            List<ProductImageStockForm> productImageStockFormList = productImageStockService.selectMany(searchForProductImageId, searchForProductImageName, searchForProductImageType).stream().map(p -> {
                p.setProductImage(productImageForDisplayPattern2(p.getProductImageId()));
                return productImageStockConversion.dto2Form(p);
            }).collect(Collectors.toList());
            radioProductImageType = initRadioProductImageType();

            model.addAttribute("productImageStockFormList", productImageStockFormList);
            model.addAttribute("radioProductImageType", radioProductImageType);

            radioProductType = initRadioProductType();
            productForm = productConversion.dto2Form(productService.selectOne(productForm.getProductId()));
            String imageForProductDetails = productImageForDisplayPattern2(productForm.getProductImageId());

            model.addAttribute("contents", "base/product/productDetail::productDetail_contents");
            model.addAttribute("imageForProductDetails", imageForProductDetails);
            model.addAttribute("radioProductType", radioProductType);

            return "base/homeLayout";
        }
        System.out.println("更新ボタンの処理");

        boolean result = productService.updateOne(productConversion.form2Dto(productForm));
        if (result) {
            model.addAttribute("result", "更新成功");
        } else {
            model.addAttribute("result", "更新失敗");
        }

        return getProductDetail(productForm, model, productForm.getProductId());
    }

    // 商品情報削除画面のPOSTメソッド
    @PostMapping(value = "/productDetail", params = "delete")
    public String postProductDelete(@ModelAttribute ProductForm productForm, Model model) {
        System.out.println("削除ボタンの処理");

        boolean result = productService.deleteOne(productForm.getProductId());

        if (result) {
            model.addAttribute("result", "削除成功");
        } else {
            model.addAttribute("result", "削除失敗");
        }

        // 商品情報画面の検索結果用に商品IDと商品名を空にする。
        productForm.setProductId("");
        productForm.setProductName("");

        return getProduct(productForm, model);
    }

    // 新規商品登録のGETメソッド
    @GetMapping("/newProductRegistration_contents")
    public String getNewProductRegistration(@ModelAttribute ProductForm productForm, Model model) {
        radioProductType = initRadioProductType();
        radioProductImageType = initRadioProductImageType();

        List<ProductImageStockForm> productImageStockFormList = productImageStockService.selectMany(productForm.getSearchForProductImageId(), productForm.getSearchForProductImageName(), productForm.getSearchForProductImageType()).stream().map(p -> {
            p.setProductImage(productImageForDisplayPattern1(p.getProductImageId()));
            return productImageStockConversion.dto2Form(p);
        }).collect(Collectors.toList());

        model.addAttribute("radioProductType", radioProductType);
        model.addAttribute("contents", "base/product/newProductRegistration::newProductRegistration_contents");
        model.addAttribute("productImageStockFormList", productImageStockFormList);
        model.addAttribute("radioProductImageType", radioProductImageType);
        return "base/homeLayout";
    }

    // 新規商品登録のPOSTメソッド
    @PostMapping("/newProductRegistration_contents")
    public String postNewProductRegistration(@ModelAttribute @Validated(ProductGroupOrder.class) ProductForm productForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return getNewProductRegistration(productForm, model);
        }
        System.out.println(productForm);

        boolean result = productService.insertOne(productConversion.form2Dto(productForm));

        //会員登録結果の判定
        if (result) {
            System.out.println("insert成功");
        } else {
            System.out.println("insert失敗");
        }

        // 商品情報画面の検索結果用に商品IDと商品名を空にする。
        productForm.setProductId("");
        productForm.setProductName("");

        return getProduct(productForm, model);
    }

    // 画像在庫検索用のpostメソッド
    @PostMapping(value = "/newProductRegistration_contents", params = "search")
    public String postProductImageStockSearch(@ModelAttribute ProductForm productForm, Model model) {
        System.out.println("商品ジメージ画像在庫検索");

        return getNewProductRegistration(productForm, model);
    }

    // 商品一覧のCSV出力処理
    @GetMapping("/productList/csv")
    public ResponseEntity<byte[]> getProductListCsv(Model model) {

        // 商品を全件取得して、CSVをサーバーに保存する
        productService.productCsvOut();

        byte[] bytes = null;

        try {
            bytes = productService.getFile("product.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "text/csv; charset=UTF-8");
        header.setContentDispositionFormData("filename", "product.csv");

        // product.csvを戻す
        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }
}
