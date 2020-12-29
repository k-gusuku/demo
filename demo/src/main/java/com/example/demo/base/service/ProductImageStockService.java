package com.example.demo.base.service;

import com.example.demo.base.dao.productimagestock.ProductImageStockDto;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * ProductImageStockのビジネスロジックを提供するモジュールを表すインターフェース.
 */
public interface ProductImageStockService {

    /**
     * 商品イメージ画像の在庫テーブルから1件データを取得.
     *
     * @param productImageId 商品イメージID
     * @return 取得データ
     */
    ProductImageStockDto selectOne(String productImageId);

    /**
     * 商品イメージ画像の在庫テーブルから複数件データを取得.
     *
     * @param productImageId 商品イメージID
     * @param productImageName 商品イメージ名
     * @param productImageType 商品イメージの種類
     * @return 取得データ
     */
    List<ProductImageStockDto> selectMany(String productImageId, String productImageName, String productImageType);

    /**
     * 商品イメージ画像の在庫テーブルに1件データを作成.
     *
     * @param productImageStockDto 作成データ
     * @return 作成の有無
     * @throws DataAccessException データ作成時に投げられるエラー
     */
    boolean insertOne(ProductImageStockDto productImageStockDto) throws DataAccessException;

    /**
     * 商品イメージ画像の在庫テーブルから1件データを更新.
     *
     * @param productImageStockDto 更新データ
     * @return 更新の有無
     * @throws DataAccessException データ更新時に投げられるエラー
     */
    boolean updateOne(ProductImageStockDto productImageStockDto) throws DataAccessException;

    /**
     * 商品イメージ画像の在庫テーブルから1件データを削除.
     *
     * @param productImageId 削除データ商品イメージID
     * @return 削除の有無
     * @throws DataAccessException データ削除時に投げられるエラー
     */
    boolean deleteOne(String productImageId) throws DataAccessException;

    /**
     * 商品情報一覧をCSV出力する.
     *
     * @throws IOException 商品情報一覧取得時に投げられるエラー.
     */
    void productCsvOut() throws DataAccessException;

    /**
     * サーバーに保存されているファイルを取得して、byte配列に変換する.
     *
     * @param fileName
     * @return byte配列に変換されたデータ
     * @throws IOException byte配列への変換時に投げられるエラー.
     */
    byte[] getFile(String fileName) throws IOException;
}
