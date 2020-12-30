package com.example.demo.base.service;

import com.example.demo.base.dao.product.ProductDto;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.List;

/**
 * Productのビジネスロジックを提供するモジュールを表すインターフェース.
 */
public interface ProductService {

    /**
     * 商品テーブルから1件データを取得する
     *
     * @param productId 商品ID
     * @return 取得データ
     */
    ProductDto selectOne(String productId);

    /**
     * 商品テーブルからデータを取得する
     *
     * @param productId 商品ID
     * @param productName 商品名
     * @return 取得データ
     */
    List<ProductDto> selectMany(String productId, String productName);

    /**
     * 商品テーブルに1件データを作成する
     *
     * @param productDto 作成データ
     * @return 作成の有無
     */
    boolean insertOne(ProductDto productDto);

    /**
     * 商品テーブルを1件更新する
     *
     * @param productDto 更新する商品ID
     * @return 更新の有無
     */
    boolean updateOne(ProductDto productDto);

    /**
     * 商品テーブルから1件データを削除する
     *
     * @param productId 削除する商品ID
     * @return 削除の有無
     */
    boolean deleteOne(String productId);

    /**
     * 商品情報一覧をCSV出力する.
     *
     * @throws IOException 商品情報一覧取得時にエラーが発生した場合に投げられる例外
     */
    void productCsvOut() throws DataAccessException;

    /**
     * サーバーに保存されているファイルを取得して、byte配列に変換する
     *
     * @param fileName
     * @return byte配列に変換されたデータ
     * @throws IOException byte配列への変換時にエラーが発生した場合に投げられる例外
     */
    byte[] getFile(String fileName) throws IOException;
}
