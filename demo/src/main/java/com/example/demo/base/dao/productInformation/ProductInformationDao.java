package com.example.demo.base.dao.productInformation;

import com.example.demo.base.domain.productInformation.ProductForm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface ProductInformationDao {

    /**
     * 商品テーブルから複数件データを取得.
     *
     * @param productId 商品ID
     * @param productName 商品名
     * @return 取得データ
     * @throws DataAccessException データ取得時に投げられるエラー
     */
    List<ProductInformationDto> selectMany(String productId, String productName) throws DataAccessException;

    /**
     * 商品テーブルに1件データを作成.
     *
     * @param productForm 作成データ
     * @return データ作成件数
     * @throws DataAccessException データ作成時に投げられるエラー
     */
    int insertOne(ProductForm productForm) throws DataAccessException;

    /**
     * 商品テーブルから1件データを取得.
     *
     * @param productId 商品ID
     * @return 取得データ
     * @throws DataAccessException データ取得時に投げられるエラー
     */
    ProductInformationDto selectOne(String productId) throws DataAccessException;

    /**
     * 商品テーブルから1件データを更新.
     *
     * @param productForm 更新データ
     * @return データ更新件数
     * @throws DataAccessException データ更新時に投げられるエラー
     */
    int updateOne(ProductForm productForm) throws DataAccessException;

    /**
     * 商品テーブルから1件データを削除.
     *
     * @param productId 削除データ商品
     * @return データ削除件数
     * @throws DataAccessException データ削除時に投げられるエラー
     */
    int deleteOne(String productId) throws DataAccessException;

    /**
     * SQL取得結果をサーバーにCSVで保存.
     *
     * @throws DataAccessException CSV保存時に投げられるエラー
     */
    void productCsvOut() throws DataAccessException;
}
