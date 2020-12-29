package com.example.demo.base.dao.productinformation.impl


import com.example.demo.base.dao.productinformation.ProductInformationDao
import com.example.demo.base.dao.productinformation.ProductInformationDto
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
class ProductInformationDaoImplSTest {
    @SpringBootTest
    static class TestSelect extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        ProductInformationDao productInformationDao

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/productinformation/impl/"))
            databaseTester.onSetup()
        }

        def "selectOne動作確認: 取得データ有"() {
            def productId = "tetris"

            when:
            def dto = productInformationDao.selectOne(productId)

            then:
            dto.each {
                assert it.productId == "tetris"
                assert it.productName == "テトリス"
                assert it.productPrice == 2900L
                assert it.productType == "ゲームソフト"
                assert it.productImageId == "image_tetris"
            }
        }

        def "selectOne動作確認: 取得データ無"() {
            def productId = "999999999"

            when:
            def dto = productInformationDao.selectOne(productId)

            then:
            dto == null
        }

        def "selectMany動作確認: 商品名検索：完全一致：取得データ有"() {
            given:
            def productId = null
            def productName = "ワンピース1巻"

            when:
            def dtoList = productInformationDao.selectMany(productId, productName)

            then:
            dtoList.size() == 1
            dtoList*.each {
                assert it.productId == "one-piece-1"
                assert it.productName == "ワンピース1巻"
                assert it.productPrice == 490L
                assert it.productType == "本"
                assert it.productImageId == "image_one-piece-1"
            }
        }

        def "selectMany動作確認: 商品名検索：部分一致：取得データ有"() {
            given:
            def productId = null
            def productName = "ワンピース"

            when:
            def dtoList = productInformationDao.selectMany(productId, productName)

            then:
            dtoList.size() == 2
            dtoList[0].each {
                assert it.productId == "one-piece-1"
                assert it.productName == "ワンピース1巻"
            }
            dtoList[1].each {
                assert it.productId == "one-piece-2"
                assert it.productName == "ワンピース2巻"
            }
        }

        def "selectMany動作確認: 商品(ID+名前)検索：取得データ有"() {
            given:
            def productId = "tetris"
            def productName = "スイッチ"

            when:
            def dtoList = productInformationDao.selectMany(productId, productName)

            then:
            dtoList.size() == 2
            dtoList[0].each {
                assert it.productId == "switch"
                assert it.productName == "スイッチ"
            }
            dtoList[1].each {
                assert it.productId == "tetris"
                assert it.productName == "テトリス"
            }
        }

        def "selectMany動作確認: 全件取得"() {
            given:
            def productId = null
            def productName = null

            when:
            def dtoList = productInformationDao.selectMany(productId, productName)

            then:
            dtoList.size() == 4
            dtoList[0].each {
                assert it.productId == "one-piece-1"
                assert it.productName == "ワンピース1巻"
            }
            dtoList[1].each {
                assert it.productId == "one-piece-2"
                assert it.productName == "ワンピース2巻"
            }
            dtoList[2].each {
                assert it.productId == "switch"
                assert it.productName == "スイッチ"
            }
            dtoList[3].each {
                assert it.productId == "tetris"
                assert it.productName == "テトリス"
            }
        }

        def "selectMany動作確認: 取得データ無"() {
            given:
            def productId = "999999999"
            def productName = "鬼滅の刃"

            when:
            def dtoList = productInformationDao.selectMany(productId, productName)

            then:
            dtoList == []
        }
    }

    @SpringBootTest
    static class TestInsertOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        ProductInformationDao productInformationDao

        def dto = null as ProductInformationDto

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.DELETE_ALL

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/productinformation/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from product")
        }

        def execute() {
            productInformationDao.insertOne(dto)
        }

        def "insertOne動作確認: 商品データを1件登録"() {
            given:
            dto = new ProductInformationDto(productId: "tetris", productName: "テトリス", productPrice: 2900L, productType: "ゲームソフト", productImageId: "image_tetris")

            when:
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 1
            assert rows.PRODUCT_ID == ["tetris"]
            assert rows.PRODUCT_NAME == ["テトリス"]
            assert rows.PRODUCT_PRICE == [2900L]
            assert rows.PRODUCT_TYPE == ["ゲームソフト"]
            assert rows.PRODUCT_IMAGE_ID == ["image_tetris"]
        }
    }

    @SpringBootTest
    static class TestUpdateOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        ProductInformationDao productInformationDao

        def dto = null as ProductInformationDto

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/productinformation/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from product where product_id = 'tetris'")
        }

        def execute() {
            productInformationDao.updateOne(dto)
        }

        def "updateOne動作確認: 商品データを1件更新"() {
            given:
            dto = new ProductInformationDto(productId: "tetris", productName: "テトリス更新", productPrice: 3900L, productType: "その他", productImageId: "image_tetris-2")

            when:
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 1
            assert rows.PRODUCT_ID == ["tetris"]
            assert rows.PRODUCT_NAME == ["テトリス更新"]
            assert rows.PRODUCT_PRICE == [3900]
            assert rows.PRODUCT_TYPE == ["その他"]
            assert rows.PRODUCT_IMAGE_ID == ["image_tetris-2"]
        }
    }

    @SpringBootTest
    static class TestDeleteOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        ProductInformationDao productInformationDao

        def productId = null as String

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/productinformation/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from product")
        }

        def execute() {
            productInformationDao.deleteOne(productId)
        }

        def "deleteOne動作確認: 商品データを1件削除"() {
            given:
            productId = "tetris"

            when:
            execute()
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 3
            assert rows.PRODUCT_ID == ["one-piece-1", "one-piece-2", "switch"]
            assert rows.PRODUCT_NAME == ["ワンピース1巻", "ワンピース2巻", "スイッチ"]
        }
    }
}
