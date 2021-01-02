package com.example.demo.base.service.impl

import com.example.demo.base.dao.employee.EmployeeDao
import com.example.demo.base.dao.employee.EmployeeDto
import com.example.demo.base.jdbc.employee.EmployeeJdbc
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

@RunWith(Enclosed)
class EmployeeServiceImplSTest {
    @SpringBootTest
    static class TestSelect extends Specification {
        def service = null as EmployeeServiceImpl

        def setup() {
            service = new EmployeeServiceImpl(Mock(EmployeeDao), Mock(EmployeeJdbc), Mock(PasswordEncoder))
        }

        def "selectOne動作確認"() {
            given:
            def employeeId = "100000000"

            when:
            def dto = service.selectOne(employeeId)

            then:
            1 * service.employeeDao.selectOne({
                employeeId == "100000000"
            } as String) >> new EmployeeDto(employeeId: "100000000", employeeName: "従業員NAME")
            and:
            dto.each {
                assert it.employeeId == "100000000"
                assert it.employeeName == "従業員NAME"
            }
        }

        def "selectMany動作確認"() {
            given:
            def employeeId = "100000000"
            def employeeName = "従業員NAME"

            when:
            def dtoList = service.selectMany(employeeId, employeeName)

            then:
            1 * service.employeeDao.selectMany(
                    { employeeId == "100000000" } as String,
                    { employeeName == "従業員NAME" } as String
            ) >> [new EmployeeDto(employeeId: "100000000", employeeName: "従業員NAME1"), new EmployeeDto(employeeId: "100000002", employeeName: "従業員NAME2")]
            and:
            dtoList.size() == 2
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
        def service = null as EmployeeServiceImpl

        def setup() {
            service = new EmployeeServiceImpl(Mock(EmployeeDao), Mock(EmployeeJdbc), Mock(PasswordEncoder))
        }

        def "insertOne動作確認: 登録成功"() {
            given:
            def dto = new EmployeeDto(employeeId: "100000000", employeeName: "従業員NAME", password: "testPass", role: "ROLE_ADMIN")

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.passwordEncoder.encode({
                it == "testPass"
            } as String) >> "testPassEncode"
            and:
            1 * service.employeeDao.insertOne({
                it.employeeId == "100000000" &&
                        it.employeeName == "従業員NAME" &&
                        it.password == "testPassEncode" &&
                        it.role == "ROLE_ADMIN"
            } as EmployeeDto) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "insertOne動作確認: 登録失敗"() {
            given:
            def dto = new EmployeeDto(employeeId: "100000000", employeeName: "従業員NAME", password: "testPass", role: "ROLE_ADMIN")

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.passwordEncoder.encode({
                it == "testPass"
            } as String) >> "testPassEncode"
            and:
            1 * service.employeeDao.insertOne({
                it.employeeId == "100000000" &&
                        it.employeeName == "従業員NAME" &&
                        it.password == "testPassEncode" &&
                        it.role == "ROLE_ADMIN"
            } as EmployeeDto) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }

    @SpringBootTest
    static class testUpdateOne extends Specification {
        def service = null as EmployeeServiceImpl

        def setup() {
            service = new EmployeeServiceImpl(Mock(EmployeeDao), Mock(EmployeeJdbc), Mock(PasswordEncoder))
        }

        def "updateOne動作確認: 更新成功"() {
            given:
            def dto = new EmployeeDto(employeeId: "100000000", employeeName: "従業員NAME")

            when:
            def result = service.updateOne(dto)

            then:
            1 * service.employeeDao.updateOne({
                it.employeeId == "100000000" &&
                        it.employeeName == "従業員NAME"
            } as EmployeeDto) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "updateOne動作確認: 更新失敗"() {
            given:
            def dto = new EmployeeDto(employeeId: "100000000", employeeName: "従業員NAME")

            when:
            def result = service.updateOne(dto)

            then:
            1 * service.employeeDao.updateOne({
                it.employeeId == "100000000" &&
                        it.employeeName == "従業員NAME"
            } as EmployeeDto) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }

    @SpringBootTest
    static class testDeleteOne extends Specification {
        def service = null as EmployeeServiceImpl

        def setup() {
            service = new EmployeeServiceImpl(Mock(EmployeeDao), Mock(EmployeeJdbc), Mock(PasswordEncoder))
        }

        def "deleteOne動作確認: 削除成功"() {
            given:
            def employeeId = "100000000"

            when:
            def result = service.deleteOne(employeeId)

            then:
            1 * service.employeeDao.deleteOne({
                employeeId == "100000000"
            } as String) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "deleteOne動作確認: 削除失敗"() {
            given:
            def employeeId = "100000000"

            when:
            def result = service.deleteOne(employeeId)

            then:
            1 * service.employeeDao.deleteOne({
                employeeId == "100000000"
            } as String) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }
}
