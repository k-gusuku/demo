package com.example.demo.base.service.impl;

import com.example.demo.base.dao.memberhistory.MemberHistoryDao;
import com.example.demo.base.dao.memberhistory.MemberHistoryDto;
import com.example.demo.base.dao.product.ProductDao;
import com.example.demo.base.dao.product.ProductDto;
import com.example.demo.base.service.MemberHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * MemberHistoryServiceの実装クラス.
 */
@Service
public class MemberHistoryServiceImpl implements MemberHistoryService {
    @Qualifier("MemberHistoryDaoImpl")
    private final MemberHistoryDao memberHistoryDao;
    private final ProductDao productDao;

    @Autowired
    public MemberHistoryServiceImpl(MemberHistoryDao memberHistoryDao, ProductDao productDao) {
        this.memberHistoryDao = memberHistoryDao;
        this.productDao = productDao;
    }

    @Override
    public List<MemberHistoryDto> selectMemberHistory(String memberId) {
        return memberHistoryDao.selectMemberHistory(memberId);
    }

    @Override
    public boolean insertOne(MemberHistoryDto memberHistoryDto) {
        memberHistoryDto.setSaleDay(new Date());
        int rowNumber = memberHistoryDao.insertOne(memberHistoryDto);

        boolean result = false;

        if (rowNumber > 0) {
            result = true;
            checkProductInventory(memberHistoryDto.getProductId());
        }
        return result;
    }

    private void checkProductInventory(String productId) {
        ProductDto productDto = productDao.selectOne(productId);
        if (productDto.getProductInventory() <= 1) {
            productDao.deleteOne(productId);
        } else {
            productDto.setProductInventory(productDto.getProductInventory() - 1);
            productDao.updateOne(productDto);
        }
    }
}
