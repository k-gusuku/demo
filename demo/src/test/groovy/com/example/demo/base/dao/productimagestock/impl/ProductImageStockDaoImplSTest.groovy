package com.example.demo.base.dao.productimagestock.impl

import com.example.demo.base.dao.productimagestock.ProductImageStockDao
import com.example.demo.base.dao.productimagestock.ProductImageStockDto
import groovy.sql.Sql
import org.dbunit.DataSourceDatabaseTester
import org.dbunit.operation.DatabaseOperation
import org.dbunit.util.fileloader.CsvDataFileLoader
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.ResourceUtils
import spock.lang.Specification

import javax.sql.DataSource

@RunWith(Enclosed)
class ProductImageStockDaoImplSTest {
    @SpringBootTest
    static class TestSelect extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        ProductImageStockDao productImageStockDao

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/productimagestock/impl/"))
            databaseTester.onSetup()
        }

        def "selectOne動作確認: 取得データ有"() {
            def productImageId = "image_tetris"

            when:
            def dto = productImageStockDao.selectOne(productImageId)

            then:
            dto.each {
                assert it.productImageId == "image_tetris"
                assert it.productImageName == "テトリス"
                assert it.productImageType == "ゲームソフト"
            }
        }

        def "selectOne動作確認: 取得データ無"() {
            def productImageId = "999999999"

            when:
            def dto = productImageStockDao.selectOne(productImageId)

            then:
            dto == null
        }

        def "selectMany動作確認: 商品イメージID検索：取得データ有"() {
            given:
            def productImageId = "image_tetris"
            def productImageName = null
            def productImageType = null

            when:
            def dtoList = productImageStockDao.selectMany(productImageId, productImageName, productImageType)

            then:
            dtoList.size() == 1
            dtoList*.each {
                assert it.productImageId == "image_tetris"
                assert it.productImageName == "テトリス"
                assert it.productImageType == "ゲームソフト"
            }
        }

        def "selectMany動作確認: 商品イメージ名検索：完全一致：取得データ有"() {
            given:
            def productImageId = null
            def productImageName = "ワンピース1巻"
            def productImageType = null

            when:
            def dtoList = productImageStockDao.selectMany(productImageId, productImageName, productImageType)

            then:
            dtoList.size() == 1
            dtoList*.each {
                assert it.productImageId == "image_one-piece-1"
                assert it.productImageName == "ワンピース1巻"
                assert it.productImageType == "本"
            }
        }

        def "selectMany動作確認: 商品イメージ名検索：部分一致：取得データ有"() {
            given:
            def productImageId = null
            def productImageName = "ワンピース"
            def productImageType = null

            when:
            def dtoList = productImageStockDao.selectMany(productImageId, productImageName, productImageType)

            then:
            dtoList.size() == 2
            dtoList[0].each {
                assert it.productImageId == "image_one-piece-1"
                assert it.productImageName == "ワンピース1巻"
                assert it.productImageType == "本"
            }
            dtoList[1].each {
                assert it.productImageId == "image_one-piece-2"
                assert it.productImageName == "ワンピース2巻"
                assert it.productImageType == "本"
            }
        }

        def "selectMany動作確認: 商品イメージの種類検索：取得データ有"() {
            given:
            def productImageId = null
            def productImageName = null
            def productImageType = "本"

            when:
            def dtoList = productImageStockDao.selectMany(productImageId, productImageName, productImageType)

            then:
            dtoList.size() == 2
            dtoList[0].each {
                assert it.productImageId == "image_one-piece-1"
                assert it.productImageName == "ワンピース1巻"
                assert it.productImageType == "本"
            }
            dtoList[1].each {
                assert it.productImageId == "image_one-piece-2"
                assert it.productImageName == "ワンピース2巻"
                assert it.productImageType == "本"
            }
        }

        def "selectMany動作確認: 商品イメージ(ID+名前+種類)検索：取得データ有"() {
            given:
            def productImageId = "image_tetris"
            def productImageName = "ワンピース1巻"
            def productImageType = "その他"

            when:
            def dtoList = productImageStockDao.selectMany(productImageId, productImageName, productImageType)

            then:
            dtoList.size() == 3
            dtoList[0].each {
                assert it.productImageId == "image_one-piece-1"
                assert it.productImageName == "ワンピース1巻"
            }
            dtoList[1].each {
                assert it.productImageId == "image_switch"
                assert it.productImageName == "スイッチ"
            }
            dtoList[2].each {
                assert it.productImageId == "image_tetris"
                assert it.productImageName == "テトリス"
            }
        }

        def "selectMany動作確認: 全件取得"() {
            given:
            def productImageId = null
            def productImageName = null
            def productImageType = null

            when:
            def dtoList = productImageStockDao.selectMany(productImageId, productImageName, productImageType)

            then:
            dtoList.size() == 4
            dtoList[0].each {
                assert it.productImageId == "image_one-piece-1"
                assert it.productImageName == "ワンピース1巻"
            }
            dtoList[1].each {
                assert it.productImageId == "image_one-piece-2"
                assert it.productImageName == "ワンピース2巻"
            }
            dtoList[2].each {
                assert it.productImageId == "image_switch"
                assert it.productImageName == "スイッチ"
            }
            dtoList[3].each {
                assert it.productImageId == "image_tetris"
                assert it.productImageName == "テトリス"
            }
        }

        def "selectMany動作確認: 取得データ無"() {
            given:
            def productImageId = "999999999"
            def productImageName = "鬼滅の刃"
            def productImageType = null

            when:
            def dtoList = productImageStockDao.selectMany(productImageId, productImageName, productImageType)

            then:
            dtoList == []
        }
    }

    @SpringBootTest
    static class TestInsertOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        ProductImageStockDao productImageStockDao

        def dto = null as ProductImageStockDto

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.DELETE_ALL

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/productimagestock/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from product_image_stock")
        }

        def execute() {
            productImageStockDao.insertOne(dto)
        }

        def "insertOne動作確認: 商品イメージ在庫1件登録"() {
            given:
            dto = new ProductImageStockDto(productImageId: "image_tetris", productImageName: "テトリス", productImageType: "ゲームソフト")

            when:
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 1
            assert rows.PRODUCT_IMAGE_ID == ["image_tetris"]
            assert rows.PRODUCT_IMAGE_NAME == ["テトリス"]
            assert rows.PRODUCT_IMAGE_TYPE == ["ゲームソフト"]
        }
    }

    @SpringBootTest
    static class TestUpdateOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        ProductImageStockDao productImageStockDao

        def dto = null as ProductImageStockDto

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/productimagestock/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from product_image_stock where product_image_id = 'image_tetris'")
        }

        def execute() {
            productImageStockDao.updateOne(dto)
        }

        def "updateOne動作確認: 商品イメージ在庫データを1件更新"() {
            given:
            dto = new ProductImageStockDto(productImageId: "image_tetris", productImageName: "テトリス更新", productImageType: "その他")

            when:
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 1
            assert rows.PRODUCT_IMAGE_ID == ["image_tetris"]
            assert rows.PRODUCT_IMAGE_NAME == ["テトリス更新"]
            assert rows.PRODUCT_IMAGE_TYPE == ["その他"]
        }
    }

    @SpringBootTest
    static class TestDeleteOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        ProductImageStockDao productImageStockDao

        def productImageId = null as String

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/productimagestock/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from product_image_stock")
        }

        def execute() {
            productImageStockDao.deleteOne(productImageId)
        }

        def "deleteOne動作確認: 商品イメージ在庫データを1件削除"() {
            given:
            productImageId = "image_tetris"

            when:
            execute()
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 3
            assert rows.PRODUCT_IMAGE_ID == ["image_one-piece-1", "image_one-piece-2", "image_switch"]
            assert rows.PRODUCT_IMAGE_NAME == ["ワンピース1巻", "ワンピース2巻", "スイッチ"]
        }
    }
}
