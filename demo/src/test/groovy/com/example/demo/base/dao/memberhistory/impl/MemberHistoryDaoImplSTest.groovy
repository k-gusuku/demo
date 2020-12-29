package com.example.demo.base.dao.memberhistory.impl

import com.example.demo.base.dao.memberhistory.MemberHistoryDao
import com.example.demo.base.dao.memberhistory.MemberHistoryDto
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
class MemberHistoryDaoImplSTest {
    @SpringBootTest
    static class TestSelectMemberHistory extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        MemberHistoryDao memberHistoryDao

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/memberhistory/impl/"))
            databaseTester.onSetup()
        }

        def "selectMemberHistory動作確認: 取得データ有"() {
            given:
            def memberId = "100000000"

            when:
            def dtoList = memberHistoryDao.selectMemberHistory(memberId)

            then:
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

        def "selectMemberHistory動作確認: 取得データ無"() {
            given:
            def memberId = "999999999"

            when:
            def dtoList = memberHistoryDao.selectMemberHistory(memberId)

            then:
            dtoList == []
        }
    }

    @SpringBootTest
    static class TestInsertOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        MemberHistoryDao memberHistoryDao

        def dto = new MemberHistoryDto()

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.DELETE_ALL

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/memberhistory/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from member_history")
        }

        def execute() {
            memberHistoryDao.insertOne(dto)
        }

        def "insertOne動作確認"() {
            given:
            dto.with {
                memberId = "100000002"
                productId = "productId_ps5"
                productName = "PS5"
                productPrice = 49000
                productType = "その他"
                productImageId = "image_ps5"
                saleDay = new Date("2020/12/27 01:01:01")
            }

            when:
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 1
            assert rows.MEMBER_ID == ["100000002"]
            assert rows.PRODUCT_ID == ["productId_ps5"]
            assert rows.PRODUCT_NAME == ["PS5"]
            assert rows.PRODUCT_PRICE == [49000]
            assert rows.PRODUCT_TYPE == ["その他"]
            assert rows.PRODUCT_IMAGE_ID == ["image_ps5"]
            assert rows.SALE_DAY == [new Date("2020/12/27 01:01:01")]
        }
    }
}
