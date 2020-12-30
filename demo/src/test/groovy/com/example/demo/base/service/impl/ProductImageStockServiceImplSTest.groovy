package com.example.demo.base.service.impl


import com.example.demo.base.dao.productimagestock.ProductImageStockDao
import com.example.demo.base.dao.productimagestock.ProductImageStockDto
import com.example.demo.base.jdbc.productimagestock.ProductImageStockJdbc
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@RunWith(Enclosed)
class ProductImageStockServiceImplSTest {
    @SpringBootTest
    static class TestSelect extends Specification {
        def service = null as ProductImageStockServiceImpl

        def setup() {
            service = new ProductImageStockServiceImpl(Mock(ProductImageStockDao), Mock(ProductImageStockJdbc))
        }

        def "selectOne動作確認"() {
            given:
            def productImageId = "image_tetris"

            when:
            def dto = service.selectOne(productImageId)

            then:
            1 * service.productImageStockDao.selectOne({
                productImageId == "image_tetris"
            } as String) >> new ProductImageStockDto(productImageId: "image_tetris", productImageName: "テトリス", productImageType: "ゲームソフト")
            and:
            dto.each {
                assert it.productImageId == "image_tetris"
                assert it.productImageName == "テトリス"
                assert it.productImageType == "ゲームソフト"
            }
        }

        def "selectMany動作確認"() {
            given:
            def productImageId = "image_tetris"
            def productImageName = "ワンピース1巻"
            def productImageType = "その他"

            when:
            def dtoList = service.selectMany(productImageId, productImageName, productImageType)

            then:
            1 * service.productImageStockDao.selectMany(
                    { productImageId == "image_tetris" } as String,
                    { productImageName == "ワンピース1巻" } as String,
                    { productImageType == "その他" } as String
            ) >> [new ProductImageStockDto(productImageId: "image_one-piece-1", productImageName: "ワンピース1巻", productImageType: "本"), new ProductImageStockDto(productImageId: "image_switch", productImageName: "スイッチ", productImageType: "その他"), new ProductImageStockDto(productImageId: "image_tetris", productImageName: "テトリス", productImageType: "ゲームソフト")]
            and:
            dtoList.size() == 3
            dtoList[0].each {
                assert it.productImageId == "image_one-piece-1"
                assert it.productImageName == "ワンピース1巻"
                assert it.productImageType == "本"

            }
            dtoList[1].each {
                assert it.productImageId == "image_switch"
                assert it.productImageName == "スイッチ"
                assert it.productImageType == "その他"

            }
            dtoList[2].each {
                assert it.productImageId == "image_tetris"
                assert it.productImageName == "テトリス"
                assert it.productImageType == "ゲームソフト"
            }
        }
    }

    @SpringBootTest
    static class testInsertOne extends Specification {
        def service = null as ProductImageStockServiceImpl

        def setup() {
            service = new ProductImageStockServiceImpl(Mock(ProductImageStockDao), Mock(ProductImageStockJdbc))
        }

        def "insertOne動作確認: 登録成功"() {
            given:
            def dto = new ProductImageStockDto(productImageId: "image_tetris", productImageName: "テトリス", productImageType: "ゲームソフト")

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.productImageStockDao.insertOne({
                it.productImageId == "image_tetris" &&
                        it.productImageName == "テトリス" &&
                        it.productImageType == "ゲームソフト"
            } as ProductImageStockDto) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "insertOne動作確認: 登録失敗"() {
            given:
            def dto = new ProductImageStockDto(productImageId: "image_tetris", productImageName: "テトリス", productImageType: "ゲームソフト")

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.productImageStockDao.insertOne({
                it.productImageId == "image_tetris" &&
                        it.productImageName == "テトリス" &&
                        it.productImageType == "ゲームソフト"
            } as ProductImageStockDto) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }

    @SpringBootTest
    static class testUpdateOne extends Specification {
        def service = null as ProductImageStockServiceImpl

        def setup() {
            service = new ProductImageStockServiceImpl(Mock(ProductImageStockDao), Mock(ProductImageStockJdbc))
        }

        def "updateOne動作確認: 更新成功"() {
            given:
            def dto = new ProductImageStockDto(productImageId: "image_tetris", productImageName: "テトリス", productImageType: "ゲームソフト")

            when:
            def result = service.updateOne(dto)

            then:
            1 * service.productImageStockDao.updateOne({
                it.productImageId == "image_tetris" &&
                        it.productImageName == "テトリス" &&
                        it.productImageType == "ゲームソフト"
            } as ProductImageStockDto) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "updateOne動作確認: 更新失敗"() {
            given:
            def dto = new ProductImageStockDto(productImageId: "image_tetris", productImageName: "テトリス", productImageType: "ゲームソフト")

            when:
            def result = service.updateOne(dto)

            then:
            1 * service.productImageStockDao.updateOne({
                it.productImageId == "image_tetris" &&
                        it.productImageName == "テトリス" &&
                        it.productImageType == "ゲームソフト"
            } as ProductImageStockDto) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }

    @SpringBootTest
    static class testDeleteOne extends Specification {
        def service = null as ProductImageStockServiceImpl

        def setup() {
            service = new ProductImageStockServiceImpl(Mock(ProductImageStockDao), Mock(ProductImageStockJdbc))
        }

        def "deleteOne動作確認: 削除成功"() {
            given:
            def productImageId = "image_tetris"

            when:
            def result = service.deleteOne(productImageId)

            then:
            1 * service.productImageStockDao.deleteOne({
                productImageId == "image_tetris"
            } as String) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "deleteOne動作確認: 削除失敗"() {
            given:
            def productImageId = "image_tetris"

            when:
            def result = service.deleteOne(productImageId)

            then:
            1 * service.productImageStockDao.deleteOne({
                productImageId == "image_tetris"
            } as String) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }
}
