package com.example.demo.base.service.impl

import com.example.demo.base.dao.member.MemberDao
import com.example.demo.base.dao.member.MemberDto
import com.example.demo.base.jdbc.member.MemberJdbc
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

@RunWith(Enclosed)
class MemberServiceImplSTest {
    @SpringBootTest
    static class TestSelect extends Specification {
        def service = null as MemberServiceImpl

        def setup() {
            service = new MemberServiceImpl(Mock(MemberDao), Mock(MemberJdbc), Mock(PasswordEncoder))
        }

        def "selectOne: 動作確認"() {
            given:
            def memberId = "100000000"

            when:
            def dto = service.selectOne(memberId)

            then:
            1 * service.memberDao.selectOne({
                memberId == "100000000"
            } as String) >> new MemberDto(memberId: "100000000", memberName: "会員NAME", birthday: new Date("1989/10/13"), age: 31, phoneNumber: "012223344", address: "東京都渋谷区")
            and:
            dto.each {
                assert it.memberId == "100000000"
                assert it.memberName == "会員NAME"
                assert it.birthday == new Date("1989/10/13")
                assert it.age == 31
                assert it.phoneNumber == "012223344"
                assert it.address == "東京都渋谷区"
            }
        }

        def "selectOneForMember: 動作確認"() {
            given:
            def memberId = "100000000"
            def memberName = "会員NAME"

            when:
            def dto = service.selectOneForMember(memberId, memberName)

            then:
            1 * service.memberDao.selectOneForMember(
                    { memberId == "100000000" } as String,
                    { memberName == "会員NAME" } as String
            ) >> new MemberDto(memberId: "100000000", memberName: "会員NAME", birthday: new Date("1989/10/13"), age: 31, phoneNumber: "012223344", address: "東京都渋谷区")
            and:
            dto.each {
                assert it.memberId == "100000000"
                assert it.memberName == "会員NAME"
                assert it.birthday == new Date("1989/10/13")
                assert it.age == 31
                assert it.phoneNumber == "012223344"
                assert it.address == "東京都渋谷区"
            }
        }

        def "selectMany: 動作確認"() {
            given:
            def memberId = "100000000"
            def memberName = "会員NAME"

            when:
            def dtoList = service.selectMany(memberId, memberName)

            then:
            1 * service.memberDao.selectMany(
                    { memberId == "100000000" } as String,
                    { memberName == "会員NAME" } as String
            ) >> [new MemberDto(memberId: "100000001", memberName: "会員NAME1", birthday: new Date("1989/10/13"), age: 31, phoneNumber: "012223344", address: "東京都渋谷区"), new MemberDto(memberId: "100000002", memberName: "会員NAME2", birthday: new Date("1990/12/25"), age: 25, phoneNumber: "012355555", address: "沖縄県那覇市")]
            and:
            dtoList[0].each {
                assert it.memberId == "100000001"
                assert it.memberName == "会員NAME1"
                assert it.birthday == new Date("1989/10/13")
                assert it.age == 31
                assert it.phoneNumber == "012223344"
                assert it.address == "東京都渋谷区"
            }
            dtoList[1].each {
                assert it.memberId == "100000002"
                assert it.memberName == "会員NAME2"
                assert it.birthday == new Date("1990/12/25")
                assert it.age == 25
                assert it.phoneNumber == "012355555"
                assert it.address == "沖縄県那覇市"
            }
        }
    }

    @SpringBootTest
    static class testInsertOne extends Specification {
        def service = null as MemberServiceImpl

        def setup() {
            service = new MemberServiceImpl(Mock(MemberDao), Mock(MemberJdbc), Mock(PasswordEncoder))
        }

        def dto = new MemberDto()

        def "insertOne: 動作確認：登録成功"() {
            given:
            dto.with {
                it.memberId = "100000000"
                it.password = "testPass"
                it.memberName = "会員NAME"
                it.role = "ROLE_GENERAL"
                it.birthday = new Date("1989/10/13")
                it.age = 31
                it.phoneNumber = "012223344"
                it.address = "東京都渋谷区"
            }

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.passwordEncoder.encode({
                it == "testPass"
            } as String) >> "testPassEncode"
            and:
            1 * service.memberDao.insertOne({
                it.memberId == "100000000" &&
                        it.password == "testPassEncode" &&
                        it.memberName == "会員NAME" &&
                        it.role == "ROLE_GENERAL" &&
                        it.birthday == new Date("1989/10/13") &&
                        it.age == 31 &&
                        it.phoneNumber == "012223344" &&
                        it.address == "東京都渋谷区"
            } as MemberDto) >> 1
            // resultがtrueである確認
            assert result
        }

        def "insertOne: 動作確認：登録失敗"() {
            given:
            dto.with {
                it.memberId = "100000000"
                it.password = "testPass"
                it.memberName = "会員NAME"
                it.role = "ROLE_GENERAL"
                it.birthday = new Date("1989/10/13")
                it.age = 31
                it.phoneNumber = "012223344"
                it.address = "東京都渋谷区"
            }

            when:
            def result = service.insertOne(dto)

            then:
            1 * service.passwordEncoder.encode({
                it == "testPass"
            } as String) >> "testPassEncode"
            and:
            1 * service.memberDao.insertOne({
                it.memberId == "100000000" &&
                        it.password == "testPassEncode" &&
                        it.memberName == "会員NAME" &&
                        it.role == "ROLE_GENERAL" &&
                        it.birthday == new Date("1989/10/13") &&
                        it.age == 31 &&
                        it.phoneNumber == "012223344" &&
                        it.address == "東京都渋谷区"
            } as MemberDto) >> 0
            // resultがfalseである確認
            assert !result
        }
    }

    @SpringBootTest
    static class testUpdate extends Specification {
        def service = null as MemberServiceImpl

        def setup() {
            service = new MemberServiceImpl(Mock(MemberDao), Mock(MemberJdbc), Mock(PasswordEncoder))
        }

        def dto = new MemberDto()

        def "updateOne: 動作確認：更新成功"() {
            given:
            dto.with {
                it.memberId = "100000000"
                it.memberName = "会員NAME"
                it.birthday = new Date("1989/10/13")
                it.age = 31
                it.phoneNumber = "012223344"
                it.address = "東京都渋谷区"
            }

            when:
            def result = service.updateOne(dto)

            then:
            1 * service.memberDao.updateOne({
                it.memberId == "100000000" &&
                        it.memberName == "会員NAME" &&
                        it.birthday == new Date("1989/10/13") &&
                        it.age == 31 &&
                        it.phoneNumber == "012223344" &&
                        it.address == "東京都渋谷区"
            } as MemberDto) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "updateOne: 動作確認：更新失敗"() {
            given:
            dto.with {
                it.memberId = "100000000"
                it.memberName = "会員NAME"
                it.birthday = new Date("1989/10/13")
                it.age = 31
                it.phoneNumber = "012223344"
                it.address = "東京都渋谷区"
            }

            when:
            def result = service.updateOne(dto)

            then:
            1 * service.memberDao.updateOne({
                it.memberId == "100000000" &&
                        it.memberName == "会員NAME" &&
                        it.birthday == new Date("1989/10/13") &&
                        it.age == 31 &&
                        it.phoneNumber == "012223344" &&
                        it.address == "東京都渋谷区"
            } as MemberDto) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }

    @SpringBootTest
    static class testDeleteOne extends Specification {
        def service = null as MemberServiceImpl

        def setup() {
            service = new MemberServiceImpl(Mock(MemberDao), Mock(MemberJdbc), Mock(PasswordEncoder))
        }

        def "deleteOne: 動作確認：削除成功"() {
            given:
            def memberId = "100000000"

            when:
            def result = service.deleteOne(memberId)

            then:
            1 * service.memberDao.deleteOne({
                memberId == "100000000"
            } as String) >> 1
            and:
            // resultがtrueである確認
            assert result
        }

        def "deleteOne: 動作確認：削除失敗"() {
            given:
            def memberId = "100000000"

            when:
            def result = service.deleteOne(memberId)

            then:
            1 * service.memberDao.deleteOne({
                memberId == "100000000"
            } as String) >> 0
            and:
            // resultがfalseである確認
            assert !result
        }
    }
}
