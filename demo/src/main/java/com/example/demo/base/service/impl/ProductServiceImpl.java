package com.example.demo.base.service.impl;

import com.example.demo.base.dao.product.ProductDao;
import com.example.demo.base.dao.product.ProductDto;
import com.example.demo.base.service.ProductService;
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

/**
 * ProductServiceの実装クラス.
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Qualifier("ProductInformationDaoImpl")
    private final ProductDao productDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public ProductDto selectOne(String productId) {
        return productDao.selectOne(productId);
    }

    @Override
    public List<ProductDto> selectMany(String productId, String productName) {
        return productDao.selectMany(productId, productName);
    }

    @Override
    public boolean insertOne(ProductDto productDto) {

        int rowNumber = productDao.insertOne(productDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean updateOne(ProductDto productDto) {

        int rowNumber = productDao.updateOne(productDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean deleteOne(String productId) {

        int rowNumber = productDao.deleteOne(productId);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public void productCsvOut() throws DataAccessException {
        //CSV出力
        productDao.productCsvOut();
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
