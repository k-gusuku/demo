package com.example.demo.base.service.impl

import com.example.demo.base.dao.employeeinformation.EmployeeInformationDao
import com.example.demo.base.dao.employeeinformation.EmployeeInformationDto
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@RunWith(Enclosed)
class EmployeeInformationServiceImplSTest {
    @SpringBootTest
    static class TestSelect extends Specification {
        def service = null as EmployeeInformationServiceImpl

        def setup() {
            service = new EmployeeInformationServiceImpl(Mock(EmployeeInformationDao))
        }

        def "selectOne: 動作確認"() {
            given:
            def employeeId = "100000000"

            when:
            def dto = service.selectOne(employeeId)

            then:
            1 * service.employeeInformationDao.selectOne({
                employeeId == "100000000"
            } as String) >> new EmployeeInformationDto(employeeId: "100000000", employeeName: "従業員NAME")
            and:
            dto.each {
                assert it.employeeId == "100000000"
                assert it.employeeName == "従業員NAME"
            }
        }

        def "selectMany: 動作確認"() {
            given:
            def employeeId = "100000000"
            def employeeName = "従業員NAME"

            when:
            def dtoList = service.selectMany(employeeId, employeeName)

            then:
            1 * service.employeeInformationDao.selectMany({
                employeeId == "100000000"
            } as String, {
                employeeName == "従業員NAME"
            } as String) >> [new EmployeeInformationDto(employeeId: "100000000", employeeName: "従業員NAME1"), new EmployeeInformationDto(employeeId: "100000002", employeeName: "従業員NAME2")]
            and:
            dtoList[0].each {
                assert it.employeeId == "100000000"
                assert it.employeeName == "従業員NAME1"
            }
            dtoList[1].each {
                assert it.employeeId == "100000002"
                assert it.employeeName == "従業員NAME2"
            }
        }
    }

    @SpringBootTest
    static class testInsertOne extends Specification {
        def service = null as EmployeeInformationServiceImpl

        def setup() {
            service = new EmployeeInformationServiceImpl(Mock(EmployeeInformationDao))
        }

        def "insertOne: 動作確認：取得有り"() {
            given:
            def dto = new EmployeeInformationDto(employeeId: "100000000", employeeName: "従業員NAME", password: "testPass", role: "ROLE_ADMIN")

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.employeeInformationDao.insertOne({
                it.employeeId == "100000000" &&
                        it.employeeName == "従業員NAME" &&
                        it.password == "testPass" &&
                        it.role == "ROLE_ADMIN"
            } as EmployeeInformationDto) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "insertOne: 動作確認：取得無し"() {
            given:
            def dto = new EmployeeInformationDto(employeeId: "100000000", employeeName: "従業員NAME", password: "testPass", role: "ROLE_ADMIN")

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.employeeInformationDao.insertOne({
                it.employeeId == "100000000" &&
                        it.employeeName == "従業員NAME" &&
                        it.password == "testPass" &&
                        it.role == "ROLE_ADMIN"
            } as EmployeeInformationDto) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }

    @SpringBootTest
    static class testUpdate extends Specification {
        def service = null as EmployeeInformationServiceImpl

        def setup() {
            service = new EmployeeInformationServiceImpl(Mock(EmployeeInformationDao))
        }

        def "updateOne: 動作確認：更新成功"() {
            given:
            def dto = new EmployeeInformationDto(employeeId: "100000000", employeeName: "従業員NAME", password: "testPass", role: "ROLE_ADMIN")

            when:
            def result = service.updateOne(dto)

            then:
            1 * service.employeeInformationDao.updateOne({
                it.employeeId == "100000000" &&
                        it.employeeName == "従業員NAME" &&
                        it.password == "testPass" &&
                        it.role == "ROLE_ADMIN"
            } as EmployeeInformationDto) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "updateOne: 動作確認：更新失敗"() {
            given:
            def dto = new EmployeeInformationDto(employeeId: "100000000", employeeName: "従業員NAME", password: "testPass", role: "ROLE_ADMIN")

            when:
            def result = service.updateOne(dto)

            then:
            1 * service.employeeInformationDao.updateOne({
                it.employeeId == "100000000" &&
                        it.employeeName == "従業員NAME" &&
                        it.password == "testPass" &&
                        it.role == "ROLE_ADMIN"
            } as EmployeeInformationDto) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }

    @SpringBootTest
    static class testDeleteOne extends Specification {
        def service = null as EmployeeInformationServiceImpl

        def setup() {
            service = new EmployeeInformationServiceImpl(Mock(EmployeeInformationDao))
        }

        def "deleteOne: 動作確認：削除成功"() {
            given:
            def employeeId = "100000000"

            when:
            def result = service.deleteOne(employeeId)

            then:
            1 * service.employeeInformationDao.deleteOne({
                employeeId == "100000000"
            } as String) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "deleteOne: 動作確認：削除失敗"() {
            given:
            def employeeId = "100000000"

            when:
            def result = service.deleteOne(employeeId)

            then:
            1 * service.employeeInformationDao.deleteOne({
                employeeId == "100000000"
            } as String) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }
}
