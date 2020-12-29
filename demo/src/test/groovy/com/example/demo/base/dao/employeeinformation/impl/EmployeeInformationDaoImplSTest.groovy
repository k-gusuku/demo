package com.example.demo.base.dao.employeeinformation.impl

import com.example.demo.base.dao.employeeinformation.EmployeeInformationDao
import com.example.demo.base.dao.employeeinformation.EmployeeInformationDto
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
import spock.lang.Unroll

import javax.sql.DataSource

@RunWith(Enclosed)
class EmployeeInformationDaoImplSTest {
    @SpringBootTest
    static class TestSelect extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        EmployeeInformationDao employeeInformationDao

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/employeeinformation/impl/"))
            databaseTester.onSetup()
        }

        def "selectOne動作確認: 取得データ有"() {
            given:
            def employeeId = "100000000"

            when:
            def dto = employeeInformationDao.selectOne(employeeId)

            then:
            dto.each {
                assert it.employeeId == "100000000"
                assert it.employeeName == "従業員NAME"
            }
        }

        def "selectOne動作確認: 取得データ無"() {
            given:
            def employeeId = "999999999"

            when:
            def dto = employeeInformationDao.selectOne(employeeId)

            then:
            assert dto == null
        }

        def "selectMany動作確認: 従業員ID検索：取得データ有"() {
            given:
            def employeeId = "100000000"
            def employeeName = null

            when:
            def dtoList = employeeInformationDao.selectMany(employeeId, employeeName)

            then:
            dtoList.size() == 1
            dtoList*.each {
                assert it.employeeId == "100000000"
                assert it.employeeName == "従業員NAME"
            }
        }

        @Unroll
        def "selectMany動作確認: 従業員名検索：#testCase：取得データ有"() {
            given:
            def employeeId = null
            def employeeName = _employeeName

            when:
            def dtoList = employeeInformationDao.selectMany(employeeId, employeeName)


            then:
            dtoList.size() == 2
            dtoList[0].each {
                assert it.employeeId == "100000000"
                assert it.employeeName == "従業員NAME"
            }
            dtoList[1].each {
                assert it.employeeId == "100000001"
                assert it.employeeName == "従業員NAME"
            }

            where:
            _employeeName | testCase
            "従業員NAME"     | "完全一致"
            "従業員"         | "部分一致"
        }

        def "selectMany動作確認: 従業員(ID+名)検索：取得データ有"() {
            given:
            def employeeId = "100000000"
            def employeeName = "従業員NAME"

            when:
            def dtoList = employeeInformationDao.selectMany(employeeId, employeeName)


            then:
            dtoList.size() == 2
            dtoList[0].each {
                assert it.employeeId == "100000000"
                assert it.employeeName == "従業員NAME"
            }
            dtoList[1].each {
                assert it.employeeId == "100000001"
                assert it.employeeName == "従業員NAME"
            }
        }

        def "selectMany動作確認: 全件取得"() {
            given:
            def employeeId = null
            def employeeName = null

            when:
            def dtoList = employeeInformationDao.selectMany(employeeId, employeeName)


            then:
            dtoList.size() == 3
            dtoList[0].each {
                assert it.employeeId == "100000000"
                assert it.employeeName == "従業員NAME"
            }
            dtoList[1].each {
                assert it.employeeId == "100000001"
                assert it.employeeName == "従業員NAME"
            }
            dtoList[2].each {
                assert it.employeeId == "100000002"
                assert it.employeeName == "具志堅次郎"
            }

        }

        def "selectMany動作確認: 取得データ無"() {
            given:
            def employeeId = "999999999"
            def employeeName = "NoData"

            when:
            def dtoList = employeeInformationDao.selectMany(employeeId, employeeName)

            then:
            assert dtoList == []
        }
    }

    @SpringBootTest
    static class TestInsertOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        EmployeeInformationDao employeeInformationDao

        def dto = new EmployeeInformationDto()

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.DELETE_ALL

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/employeeinformation/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from employee")
        }

        def execute() {
            employeeInformationDao.insertOne(dto)
        }

        def "selectOne動作確認: 従業員データ1件登録"() {
            given:
            dto.with {
                employeeId = "100000001"
                employeeName = "従業員NAME"
                password = "password"
                role = "ROLE_ADMIN"
            }

            when:
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 1
            assert rows.EMPLOYEE_ID == ["100000001"]
            assert rows.EMPLOYEE_NAME == ["従業員NAME"]
            assert rows.PASSWORD == ["password"]
            assert rows.ROLE == ["ROLE_ADMIN"]
        }
    }

    @SpringBootTest
    static class TestUpdateOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        EmployeeInformationDao employeeInformationDao

        def dto = new EmployeeInformationDto()

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/employeeinformation/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from employee where employee_id = '100000000'")
        }

        def execute() {
            employeeInformationDao.updateOne(dto)
        }

        def "updateOne動作確認: 1件従業員データ更新"() {
            given:
            dto.with {
                employeeId = "100000000"
                employeeName = "従業員NAME更新"
            }

            when:
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 1
            assert rows.EMPLOYEE_ID == ["100000000"]
            assert rows.EMPLOYEE_NAME == ["従業員NAME更新"]
        }
    }

    @SpringBootTest
    static class TestDeleteOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        EmployeeInformationDao employeeInformationDao

        def employeeId = null as String

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/employeeinformation/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from employee")
        }

        def execute() {
            employeeInformationDao.deleteOne(employeeId)
        }

        def "updateOne動作確認: 従業員データ更新"() {
            given:
            employeeId = "100000000"

            when:
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 2
            assert rows.EMPLOYEE_ID == ["100000001", "100000002"]
        }
    }
}
