package com.example.demo.base.service.impl

import com.example.demo.base.dao.product.ProductDao
import com.example.demo.base.dao.product.ProductDto
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@RunWith(Enclosed)
class ProductServiceImplSTest {
    @SpringBootTest
    static class TestSelect extends Specification {
        def service = null as ProductServiceImpl

        def setup() {
            service = new ProductServiceImpl(Mock(ProductDao))
        }

        def "selectOne動作確認"() {
            given:
            def productId = "tetris"

            when:
            def dto = service.selectOne(productId)

            then:
            1 * service.productDao.selectOne({
                productId == "tetris"
            } as String) >> new ProductDto(productId: "tetris", productName: "テトリス", productPrice: 2900L, productType: "ゲームソフト", productImageId: "image_tetris")
            and:
            dto.each {
                assert it.productId == "tetris"
                assert it.productName == "テトリス"
                assert it.productPrice == 2900L
                assert it.productType == "ゲームソフト"
                assert it.productImageId == "image_tetris"
            }
        }

        def "selectMany動作確認"() {
            given:
            def productId = "tetris"
            def productName = "スイッチ"

            when:
            def dtoList = service.selectMany(productId, productName)

            then:
            1 * service.productDao.selectMany(
                    { productId == "tetris" } as String,
                    { productName == "スイッチ" } as String
            ) >> [new ProductDto(productId: "tetris", productName: "テトリス", productPrice: 2900L, productType: "ゲームソフト", productImageId: "image_tetris"), new ProductDto(productId: "switch", productName: "スイッチ", productPrice: 29000L, productType: "その他", productImageId: "image_switch")]
            and:
            dtoList.size() == 2
            dtoList[0].each {
                assert it.productId == "tetris"
                assert it.productName == "テトリス"
                assert it.productPrice == 2900L
                assert it.productType == "ゲームソフト"
                assert it.productImageId == "image_tetris"

            }
            dtoList[1].each {
                assert it.productId == "switch"
                assert it.productName == "スイッチ"
                assert it.productPrice == 29000L
                assert it.productType == "その他"
                assert it.productImageId == "image_switch"

            }
        }
    }

    @SpringBootTest
    static class testInsertOne extends Specification {
        def service = null as ProductServiceImpl

        def setup() {
            service = new ProductServiceImpl(Mock(ProductDao))
        }

        def dto = new ProductDto()

        def "insertOne動作確認: 登録成功"() {
            given:
            dto.with {
                it.productId = "tetris"
                it.productName = "テトリス"
                it.productPrice = 2900L
                it.productType = "ゲームソフト"
                it.productImageId = "image_tetris"
            }

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.productDao.insertOne({
                it.productId == "tetris" &&
                        it.productName == "テトリス" &&
                        it.productPrice == 2900L &&
                        it.productType == "ゲームソフト" &&
                        it.productImageId == "image_tetris"
            } as ProductDto) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "insertOne動作確認: 登録失敗"() {
            given:
            dto.with {
                it.productId = "tetris"
                it.productName = "テトリス"
                it.productPrice = 2900L
                it.productType = "ゲームソフト"
                it.productImageId = "image_tetris"
            }

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.productDao.insertOne({
                it.productId == "tetris" &&
                        it.productName == "テトリス" &&
                        it.productPrice == 2900L &&
                        it.productType == "ゲームソフト" &&
                        it.productImageId == "image_tetris"
            } as ProductDto) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }

    @SpringBootTest
    static class testUpdateOne extends Specification {
        def service = null as ProductServiceImpl

        def setup() {
            service = new ProductServiceImpl(Mock(ProductDao))
        }

        def dto = new ProductDto()

        def "insertOne動作確認: 登録成功"() {
            given:
            dto.with {
                it.productId = "tetris"
                it.productName = "テトリス"
                it.productPrice = 2900L
                it.productType = "ゲームソフト"
                it.productImageId = "image_tetris"
            }

            when:
            def result = service.updateOne(dto)

            then:
            1 * service.productDao.updateOne({
                it.productId == "tetris" &&
                        it.productName == "テトリス" &&
                        it.productPrice == 2900L &&
                        it.productType == "ゲームソフト" &&
                        it.productImageId == "image_tetris"
            } as ProductDto) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "insertOne動作確認: 登録失敗"() {
            given:
            dto.with {
                it.productId = "tetris"
                it.productName = "テトリス"
                it.productPrice = 2900L
                it.productType = "ゲームソフト"
                it.productImageId = "image_tetris"
            }

            when:
            def result = service.updateOne(dto)

            then:
            1 * service.productDao.updateOne({
                it.productId == "tetris" &&
                        it.productName == "テトリス" &&
                        it.productPrice == 2900L &&
                        it.productType == "ゲームソフト" &&
                        it.productImageId == "image_tetris"
            } as ProductDto) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }

    @SpringBootTest
    static class testDeleteOne extends Specification {
        def service = null as ProductServiceImpl

        def setup() {
            service = new ProductServiceImpl(Mock(ProductDao))
        }

        def "deleteOne動作確認: 削除成功"() {
            given:
            def productId = "tetris"

            when:
            def result = service.deleteOne(productId)

            then:
            1 * service.productDao.deleteOne({
                productId == "tetris"
            } as String) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "deleteOne動作確認: 削除失敗"() {
            given:
            def productId = "tetris"

            when:
            def result = service.deleteOne(productId)

            then:
            1 * service.productDao.deleteOne({
                productId == "tetris"
            } as String) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }
}
