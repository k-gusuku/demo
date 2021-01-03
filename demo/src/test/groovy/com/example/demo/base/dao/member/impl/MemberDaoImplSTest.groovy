package com.example.demo.base.dao.member.impl

import com.example.demo.base.dao.member.MemberDao
import com.example.demo.base.dao.member.MemberDto
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
class MemberDaoImplSTest {
    @SpringBootTest
    static class TestSelect extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        MemberDao memberDao

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/member/impl/"))
            databaseTester.onSetup()
        }

        def "selectOne動作確認: 取得データ有"() {
            def memberId = "100000000"

            when:
            def dto = memberDao.selectOne(memberId)

            then:
            dto.each {
                assert it.memberId == "100000000"
                assert it.memberName == "会員NAME"
                assert it.birthday == new Date("1989/10/13")
                assert it.age == 31L
                assert it.phoneNumber == "012223344"
                assert it.address == "東京都渋谷区"
            }
        }

        def "selectOne動作確認: 取得データ無"() {
            def memberId = "999999999"

            when:
            def dto = memberDao.selectOne(memberId)

            then:
            dto == null
        }

        def "selectOneForMember: 取得データ有"() {
            def memberId = "100000000"

            when:
            def dto = memberDao.selectOneForMember(memberId)

            then:
            dto.each {
                assert it.memberId == "100000000"
                assert it.memberName == "会員NAME"
                assert it.birthday == new Date("1989/10/13")
                assert it.age == 31L
                assert it.phoneNumber == "012223344"
                assert it.address == "東京都渋谷区"
            }
        }

        @Unroll
        def "selectOneForMember: 取得データ無：#testCase"() {
            def memberId = "999999999"

            when:
            def dto = memberDao.selectOneForMember(memberId)

            then:
            dto == null
        }

        def "selectMany動作確認: 会員ID検索：取得データ有"() {
            given:
            def memberId = "100000000"
            def memberName = null

            when:
            def dtoList = memberDao.selectMany(memberId, memberName)

            then:
            dtoList.size() == 1
            dtoList*.each {
                assert it.memberId == "100000000"
                assert it.memberName == "会員NAME"
                assert it.birthday == new Date("1989/10/13")
                assert it.age == 31
                assert it.phoneNumber == "012223344"
                assert it.address == "東京都渋谷区"
            }
        }

        @Unroll
        def "selectMany動作確認: 会員名検索：#testCase：取得データ有"() {
            given:
            def memberId = null
            def memberName = _memberName

            when:
            def dtoList = memberDao.selectMany(memberId, memberName)

            then:
            dtoList.size() == 2
            dtoList[0].each {
                assert it.memberId == "100000000"
                assert it.memberName == "会員NAME"
            }
            dtoList[1].each {
                assert it.memberId == "100000001"
                assert it.memberName == "会員NAME"
            }

            where:
            _memberName | testCase
            "会員NAME"    | "完全一致"
            "会員"        | "部分一致"
        }

        def "selectMany動作確認: 会員(ID+名)検索：取得データ有"() {
            given:
            def memberId = "100000000"
            def memberName = "会員NAME"

            when:
            def dtoList = memberDao.selectMany(memberId, memberName)


            then:
            dtoList.size() == 2
            dtoList[0].each {
                assert it.memberId == "100000000"
                assert it.memberName == "会員NAME"
            }
            dtoList[1].each {
                assert it.memberId == "100000001"
                assert it.memberName == "会員NAME"
            }
        }

        def "selectMany動作確認: 全件取得"() {
            given:
            def memberId = null
            def memberName = null

            when:
            def dtoList = memberDao.selectMany(memberId, memberName)


            then:
            dtoList.size() == 3
            dtoList[0].each {
                assert it.memberId == "100000000"
                assert it.memberName == "会員NAME"
            }
            dtoList[1].each {
                assert it.memberId == "100000001"
                assert it.memberName == "会員NAME"
            }
            dtoList[2].each {
                assert it.memberId == "100000002"
                assert it.memberName == "具志堅太郎"
            }
        }

        def "selectMany動作確認: 取得データ無"() {
            given:
            def memberId = "999999999"
            def memberName = "NoData"

            when:
            def dtoList = memberDao.selectMany(memberId, memberName)

            then:
            assert dtoList == []
        }
    }

    @SpringBootTest
    static class TestInsertOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        MemberDao memberDao

        def dto = new MemberDto()

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.DELETE_ALL

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/member/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from member")
        }

        def execute() {
            memberDao.insertOne(dto)
        }

        def "insertOne動作確認: 会員データ1件登録"() {
            given:
            dto.with {
                memberId = "100000001"
                memberName = "会員NAME"
                password = "password"
                role = "ROLE_GENERAL"
                birthday = new Date("1989/10/13")
                age = 31
                phoneNumber = "012223344"
                address = "東京都渋谷区"
            }

            when:
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 1
            assert rows.MEMBER_ID == ["100000001"]
            assert rows.PASSWORD == ["password"]
            assert rows.MEMBER_NAME == ["会員NAME"]
            assert rows.ROLE == ["ROLE_GENERAL"]
            assert rows.BIRTHDAY == [new Date("1989/10/13")]
            assert rows.AGE == [31]
            assert rows.PHONE_NUMBER == ["012223344"]
            assert rows.ADDRESS == ["東京都渋谷区"]
        }
    }

    @SpringBootTest
    static class TestUpdateOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        MemberDao memberDao

        def dto = new MemberDto()

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/member/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from member where member_id = '100000000'")
        }

        def execute() {
            memberDao.updateOne(dto)
        }

        def "updateOne動作確認: 1件会員データ更新"() {
            given:
            dto.with {
                memberId = "100000000"
                memberName = "会員NAME更新"
                birthday = new Date("2020/12/28")
                age = 0
                phoneNumber = "123456789"
                address = "福岡県早良区"
            }

            when:
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 1
            assert rows.MEMBER_ID == ["100000000"]
            assert rows.MEMBER_NAME == ["会員NAME更新"]
            assert rows.BIRTHDAY == [new Date("2020/12/28")]
            assert rows.AGE == [0]
            assert rows.PHONE_NUMBER == ["123456789"]
            assert rows.ADDRESS == ["福岡県早良区"]
        }
    }

    @SpringBootTest
    static class TestDeleteOne extends Specification {
        @Autowired
        DataSource dataSource

        @Autowired
        MemberDao memberDao

        def memberId = null as String

        def setup() {
            def databaseTester = new DataSourceDatabaseTester(dataSource)
            databaseTester.setUpOperation = DatabaseOperation.CLEAN_INSERT

            def loader = new CsvDataFileLoader()
            databaseTester.dataSet = loader.loadDataSet(ResourceUtils.getURL("src/test/groovy/com/example/demo/base/dao/member/impl/"))
            databaseTester.onSetup()
        }

        def getRows() {
            def sql = Sql.newInstance("jdbc:mysql://localhost:3306/testdb2?serverTimezone=JST", "testuser2", "testpass2", "com.mysql.cj.jdbc.Driver")
            sql.rows("select * from member")
        }

        def execute() {
            memberDao.deleteOne(memberId)
        }

        def "deleteOne動作確認: 会員データを1件削除"() {
            given:
            memberId = "100000000"

            when:
            execute()
            def rows = getRows()

            then:
            assert rows.size() == 2
            assert rows.MEMBER_ID == ["100000001", "100000002"]
        }
    }
}
