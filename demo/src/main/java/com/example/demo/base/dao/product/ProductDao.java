package com.example.demo.base.dao.product;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * productテーブルにアクセスするためのDataAccessObjectを表すインターフェース.
 */
@Mapper
public interface ProductDao {

    /**
     * 商品テーブルから1件データを取得
     *
     * @param productId 商品ID
     * @return 取得データ
     * @throws DataAccessException データ取得時にエラーが発生した場合に投げられる例外
     */
    ProductDto selectOne(String productId) throws DataAccessException;

    /**
     * 商品テーブルから複数件データを取得
     *
     * @param productId 商品ID
     * @param productName 商品名
     * @return 取得データ
     * @throws DataAccessException データ取得時にエラーが発生した場合に投げられる例外
     */
    List<ProductDto> selectMany(String productId, String productName) throws DataAccessException;

    /**
     * 商品テーブルに1件データを作成
     *
     * @param productDto 作成データ
     * @return データ作成件数
     * @throws DataAccessException データ作成時にエラーが発生した場合に投げられる例外
     */
    int insertOne(ProductDto productDto) throws DataAccessException;

    /**
     * 商品テーブルから1件データを更新
     *
     * @param productDto 更新データ
     * @return データ更新件数
     * @throws DataAccessException データ更新時にエラーが発生した場合に投げられる例外
     */
    int updateOne(ProductDto productDto) throws DataAccessException;

    /**
     * 商品テーブルから1件データを削除
     *
     * @param productId 削除データ商品ID
     * @return データ削除件数
     * @throws DataAccessException データ削除時にエラーが発生した場合に投げられる例外
     */
    int deleteOne(String productId) throws DataAccessException;
}
