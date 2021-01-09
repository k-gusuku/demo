package com.example.demo.base.service.impl

import com.example.demo.base.dao.memberhistory.MemberHistoryDao
import com.example.demo.base.dao.memberhistory.MemberHistoryDto
import com.example.demo.base.dao.product.ProductDao
import com.example.demo.base.dao.product.ProductDto
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@RunWith(Enclosed)
class MemberHistoryServiceImplSTest {
    @SpringBootTest
    static class testSelectMemberHistory extends Specification {
        def service = null as MemberHistoryServiceImpl

        def setup() {
            service = new MemberHistoryServiceImpl(Mock(MemberHistoryDao), Mock(ProductDao))
        }

        def "selectMemberHistory: 動作確認：取得データ有"() {
            given:
            def memberId = "100000000"

            when:
            def dtoList = service.selectMemberHistory(memberId)

            then:
            1 * service.memberHistoryDao.selectMemberHistory({
                memberId == "100000000"
            } as String) >> [new MemberHistoryDto(memberId: "100000000", productId: "productId_tetris", productName: "テトリス", productPrice: 3900, productType: "ゲームソフト", productImageId: "image_tetris", saleDay: new Date("2020/12/24 01:01:01")),
                             new MemberHistoryDto(memberId: "100000000", productId: "productId_switch", productName: "スイッチ", productPrice: 39000, productType: "その他", productImageId: "image_switch", saleDay: new Date("2020/12/25 01:01:01"))]
            and:
            dtoList.size() == 2
            dtoList[0].each {
                assert it.memberId == "100000000"
                assert it.productId == "productId_tetris"
                assert it.productName == "テトリス"
                assert it.productPrice == 3900
                assert it.productType == "ゲームソフト"
                assert it.productImageId == "image_tetris"
                assert it.saleDay == new Date("2020/12/24 01:01:01")
            }
            dtoList[1].each {
                assert it.memberId == "100000000"
                assert it.productId == "productId_switch"
                assert it.productName == "スイッチ"
                assert it.productPrice == 39000
                assert it.productType == "その他"
                assert it.productImageId == "image_switch"
                assert it.saleDay == new Date("2020/12/25 01:01:01")
            }
        }
    }

    @SpringBootTest
    static class testInsertOne extends Specification {
        def service = null as MemberHistoryServiceImpl

        def setup() {
            service = new MemberHistoryServiceImpl(Mock(MemberHistoryDao), Mock(ProductDao))
        }

        def dto = new MemberHistoryDto()

        def "insertOne: 動作確認：登録成功：商品の商品在庫数が1つより多い"() {
            given:
            dto.with {
                memberId = "100000000"
                productId = "productId_tetris"
                productName = "テトリス"
                productPrice = 3900
                productType = "ゲームソフト"
                productImageId = "image_tetris"
            }

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.memberHistoryDao.insertOne({
                it.memberId == "100000000" &&
                        it.productId == "productId_tetris" &&
                        it.productName == "テトリス" &&
                        it.productPrice == 3900 &&
                        it.productType == "ゲームソフト" &&
                        it.productImageId == "image_tetris" &&
                        it.saleDay.format("yyyy/MM/dd HH:mm:ss") == new Date().format("yyyy/MM/dd HH:mm:ss")
            } as MemberHistoryDto) >> 1
            1 * service.productDao.selectOne({
                it == "productId_tetris"
            } as String) >> new ProductDto(productId: "productId_tetris", productInventory: 3L)
            1 * service.productDao.updateOne({
                it.productId == "productId_tetris" &&
                        it.productInventory == 2L
            } as ProductDto)
            and:
            // resultがtrueである確認
            assert result
        }

        def "insertOne: 動作確認：登録成功：商品の商品在庫数が1つ以下"() {
            given:
            dto.with {
                memberId = "100000000"
                productId = "productId_tetris"
                productName = "テトリス"
                productPrice = 3900
                productType = "ゲームソフト"
                productImageId = "image_tetris"
            }

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.memberHistoryDao.insertOne({
                it.memberId == "100000000" &&
                        it.productId == "productId_tetris" &&
                        it.productName == "テトリス" &&
                        it.productPrice == 3900 &&
                        it.productType == "ゲームソフト" &&
                        it.productImageId == "image_tetris" &&
                        it.saleDay.format("yyyy/MM/dd HH:mm:ss") == new Date().format("yyyy/MM/dd HH:mm:ss")
            } as MemberHistoryDto) >> 1
            1 * service.productDao.selectOne({
                it == "productId_tetris"
            } as String) >> new ProductDto(productId: "productId_tetris", productInventory: 1L)
            1 * service.productDao.deleteOne({
                it == "productId_tetris"
            } as String)
            and:
            // resultがtrueである確認
            assert result
        }

        def "insertOne: 動作確認：登録失敗"() {
            given:
            dto.with {
                memberId = "100000000"
                productId = "productId_tetris"
                productName = "テトリス"
                productPrice = 3900
                productType = "ゲームソフト"
                productImageId = "image_tetris"
            }

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.memberHistoryDao.insertOne({
                it.memberId == "100000000" &&
                        it.productId == "productId_tetris" &&
                        it.productName == "テトリス" &&
                        it.productPrice == 3900 &&
                        it.productType == "ゲームソフト" &&
                        it.productImageId == "image_tetris" &&
                        it.saleDay.format("yyyy/MM/dd HH:mm:ss") == new Date().format("yyyy/MM/dd HH:mm:ss")
            } as MemberHistoryDto) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }
}
