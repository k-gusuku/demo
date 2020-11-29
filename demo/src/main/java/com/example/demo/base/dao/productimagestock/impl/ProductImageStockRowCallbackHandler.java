package com.example.demo.base.dao.productimagestock.impl;

import org.springframework.jdbc.core.RowCallbackHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductImageStockRowCallbackHandler implements RowCallbackHandler {

    public void processRow(ResultSet rs) throws SQLException {
        try {

            File file = new File("productImageStock.csv");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            do {

                String str = rs.getString("PRODUCT_IMAGE_ID") + ","
                        + rs.getString("PRODUCT_IMAGE_NAME") + ","
                        + rs.getString("PRODUCT_IMAGE_TYPE");

                bw.write(str);
                bw.newLine();

            } while (rs.next());

            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }
    }
}
