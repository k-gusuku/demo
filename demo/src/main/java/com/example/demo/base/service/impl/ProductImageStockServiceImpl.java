package com.example.demo.base.service.impl;

import com.example.demo.base.dao.productimagestock.ProductImageStockDao;
import com.example.demo.base.dao.productimagestock.ProductImageStockDto;
import com.example.demo.base.service.ProductImageStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ProductImageStockServiceImpl implements ProductImageStockService {

    @Autowired
    @Qualifier("ProductImageStockDaoImpl")
    ProductImageStockDao dao;

    @Override
    public ProductImageStockDto selectOne(String productImageId) {
        return dao.selectOne(productImageId);
    }

    @Override
    public List<ProductImageStockDto> selectMany(String productImageId, String productImageName, String productImageType) {
        return dao.selectMany(productImageId, productImageName, productImageType);
    }

    @Override
    public boolean insertOne(ProductImageStockDto productImageStockDto) {

        int rowNumber = dao.insertOne(productImageStockDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean updateOne(ProductImageStockDto productImageStockDto) {

        int rowNumber = dao.updateOne(productImageStockDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean deleteOne(String productImageId) {

        int rowNumber = dao.deleteOne(productImageId);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public void productCsvOut() throws DataAccessException {
        //CSV出力
        dao.productImageStockCsvOut();
    }

    @Override
    public byte[] getFile(String fileName) throws IOException {

        // ファイルシステム(デフォルト)の取得
        FileSystem fs = FileSystems.getDefault();

        // ファイル取得
        Path p = fs.getPath(fileName);

        // ファイルをbyte配列に変換
        byte[] bytes = Files.readAllBytes(p);

        return bytes;
    }
}
