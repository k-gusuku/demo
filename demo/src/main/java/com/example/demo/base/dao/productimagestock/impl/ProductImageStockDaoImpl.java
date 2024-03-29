package com.example.demo.base.dao.productimagestock.impl;

import com.example.demo.base.dao.productimagestock.ProductImageStockDao;
import com.example.demo.base.dao.productimagestock.ProductImageStockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductImageStockDaoの実装クラス.
 */
@Repository("ProductImageStockDaoImpl")
public class ProductImageStockDaoImpl implements ProductImageStockDao {
    @Lazy
    private final ProductImageStockDao productImageStockDao;

    @Autowired
    public ProductImageStockDaoImpl(ProductImageStockDao productImageStockDao) {
        this.productImageStockDao = productImageStockDao;
    }

    @Override
    public ProductImageStockDto selectOne(String productImageId) throws DataAccessException {
        return productImageStockDao.selectOne(productImageId);
    }

    @Override
    public List<ProductImageStockDto> selectMany(String productImageId, String productImageName, String productImageType) throws DataAccessException {
        return productImageStockDao.selectMany(productImageId, productImageName, productImageType);
    }

    @Override
    public int insertOne(ProductImageStockDto productImageStockDto) throws DataAccessException {
        return productImageStockDao.insertOne(productImageStockDto);
    }

    @Override
    public int updateOne(ProductImageStockDto productImageStockDto) throws DataAccessException {
        return productImageStockDao.updateOne(productImageStockDto);
    }

    @Override
    public int deleteOne(String productImageId) throws DataAccessException {
        return productImageStockDao.deleteOne(productImageId);
    }
}
