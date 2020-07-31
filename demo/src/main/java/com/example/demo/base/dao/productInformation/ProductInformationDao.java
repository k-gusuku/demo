package com.example.demo.base.dao.productInformation;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface ProductInformationDao {

    List<ProductInformationDto> selectMany(String productId, String productName) throws DataAccessException;
}
