package com.example.demo.base.service.impl;

import com.example.demo.base.dao.productinformation.ProductInformationDao;
import com.example.demo.base.dao.productinformation.ProductInformationDto;
import com.example.demo.base.service.ProductInformationService;
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
public class ProductInformationServiceImpl implements ProductInformationService {
    @Qualifier("ProductInformationDaoImpl")
    private final ProductInformationDao productInformationDao;

    @Autowired
    public ProductInformationServiceImpl(ProductInformationDao productInformationDao) {
        this.productInformationDao = productInformationDao;
    }

    @Override
    public ProductInformationDto selectOne(String productId) {
        return productInformationDao.selectOne(productId);
    }

    @Override
    public List<ProductInformationDto> selectMany(String productId, String productName) {
        return productInformationDao.selectMany(productId, productName);
    }

    @Override
    public boolean insertOne(ProductInformationDto productInformationDto) {

        int rowNumber = productInformationDao.insertOne(productInformationDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean updateOne(ProductInformationDto productInformationDto) {

        int rowNumber = productInformationDao.updateOne(productInformationDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public boolean deleteOne(String productId) {

        int rowNumber = productInformationDao.deleteOne(productId);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
        }
        return result;
    }

    @Override
    public void productCsvOut() throws DataAccessException {
        //CSV出力
        productInformationDao.productCsvOut();
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
