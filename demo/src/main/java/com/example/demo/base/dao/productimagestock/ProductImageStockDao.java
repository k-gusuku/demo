package com.example.demo.base.dao.productimagestock;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * productImageStockテーブルにアクセスするためのDataAccessObjectを表すインターフェース.
 */
@Mapper
public interface ProductImageStockDao {

    /**
     * 商品イメージ画像の在庫テーブルから1件データを取得
     *
     * @param productImageId 商品イメージID
     * @return 取得データ
     * @throws DataAccessException データ取得時にエラーが発生した場合に投げられる例外
     */
    ProductImageStockDto selectOne(String productImageId) throws DataAccessException;

    /**
     * 商品イメージ画像の在庫テーブルから複数件データを取得
     *
     * @param productImageId   商品イメージID
     * @param productImageName 商品イメージ名
     * @param productImageType 商品イメージの種類
     * @return 取得データ
     * @throws DataAccessException データ取得時にエラーが発生した場合に投げられる例外
     */
    List<ProductImageStockDto> selectMany(String productImageId, String productImageName, String productImageType) throws DataAccessException;

    /**
     * 商品イメージ画像の在庫テーブルに1件データを作成
     *
     * @param productImageStockDto 作成データ
     * @return データ作成件数
     * @throws DataAccessException データ作成時にエラーが発生した場合に投げられる例外
     */
    int insertOne(ProductImageStockDto productImageStockDto) throws DataAccessException;

    /**
     * 商品イメージ画像の在庫テーブルから1件データを更新
     *
     * @param productImageStockDto 更新データ
     * @return データ更新件数
     * @throws DataAccessException データ更新時にエラーが発生した場合に投げられる例外
     */
    int updateOne(ProductImageStockDto productImageStockDto) throws DataAccessException;

    /**
     * 商品イメージ画像の在庫テーブルから1件データを削除
     *
     * @param productImageId 削除データ商品イメージID
     * @return データ削除件数
     * @throws DataAccessException データ削除時にエラーが発生した場合に投げられる例外
     */
    int deleteOne(String productImageId) throws DataAccessException;
}
