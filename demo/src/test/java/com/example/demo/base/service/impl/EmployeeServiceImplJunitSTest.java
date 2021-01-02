package com.example.demo.base.service.impl;

import com.example.demo.base.dao.employee.EmployeeDao;
import com.example.demo.base.dao.employee.EmployeeDto;
import com.example.demo.base.jdbc.employee.EmployeeJdbc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmployeeServiceImplJunitSTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    EmployeeDao employeeDaoMock;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    EmployeeJdbc employeeJdbcMock;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("selectOne動作確認")
    void testSelectOne() {

        EmployeeServiceImpl service = initTestService();
        ArgumentCaptor<String> employeeIdCaptor = ArgumentCaptor.forClass(String.class);

        // テストデータ設定
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId("100000000");
        employeeDto.setEmployeeName("従業員NAME");
        String employeeId = "100000000";

        // Mock設定
        when(employeeDaoMock.selectOne(employeeId)).thenReturn(employeeDto);

        // 実行
        EmployeeDto actual = service.selectOne(employeeId);

        // 検証
        verify(employeeDaoMock, times(1)).selectOne(employeeIdCaptor.capture());
        assertEquals(employeeId, employeeIdCaptor.getValue());
        assertEquals(employeeDto, actual);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("selectMany動作確認")
    void testSelectMany() {

        EmployeeServiceImpl service = initTestService();
        ArgumentCaptor<String> employeeIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> employeeNameCaptor = ArgumentCaptor.forClass(String.class);

        // テストデータ設定
        EmployeeDto employeeDto1 = new EmployeeDto();
        employeeDto1.setEmployeeId("100000001");
        employeeDto1.setEmployeeName("従業員NAME1");
        EmployeeDto employeeDto2 = new EmployeeDto();
        employeeDto1.setEmployeeId("100000002");
        employeeDto1.setEmployeeName("従業員NAME2");
        List<EmployeeDto> employeeDtoList = Arrays.asList(employeeDto1, employeeDto2);
        String employeeId = "100000001";
        String employeeName = "従業員NAME";

        // Mock設定
        when(employeeDaoMock.selectMany(employeeId, employeeName)).thenReturn(employeeDtoList);

        // 実行
        List<EmployeeDto> actual = service.selectMany(employeeId, employeeName);

        // 検証
        verify(employeeDaoMock, times(1)).selectMany(employeeIdCaptor.capture(), employeeNameCaptor.capture());
        assertEquals(employeeId, employeeIdCaptor.getValue());
        assertEquals(employeeName, employeeNameCaptor.getValue());
        assertEquals(employeeDtoList, actual);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("insertOne動作確認: 登録成功")
    void testInsertOne1() {

        EmployeeServiceImpl service = initTestService();
        ArgumentCaptor<EmployeeDto> employeeDtoCaptor = ArgumentCaptor.forClass(EmployeeDto.class);

        // テストデータ設定
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId("100000000");
        employeeDto.setEmployeeName("従業員NAME");
        employeeDto.setPassword("testPass");
        employeeDto.setRole("ROLE_ADMIN");
        EmployeeDto resultEmployeeDto = new EmployeeDto();
        resultEmployeeDto.setEmployeeId("100000000");
        resultEmployeeDto.setEmployeeName("従業員NAME");
        resultEmployeeDto.setPassword("testPassEncode");
        resultEmployeeDto.setRole("ROLE_ADMIN");

        // Mock設定
        when(passwordEncoder.encode("testPass")).thenReturn("testPassEncode");
        when(employeeDaoMock.insertOne(employeeDto)).thenReturn(1);

        // 実行
        Boolean actual = service.insertOne(employeeDto);

        // 検証
        verify(employeeDaoMock, times(1)).insertOne(employeeDtoCaptor.capture());
        assertEquals(resultEmployeeDto, employeeDtoCaptor.getValue());
        assertEquals(true, actual);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("insertOne動作確認: 登録失敗")
    void testInsertOne2() {

        EmployeeServiceImpl service = initTestService();
        ArgumentCaptor<EmployeeDto> employeeDtoCaptor = ArgumentCaptor.forClass(EmployeeDto.class);

        // テストデータ設定
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId("100000000");
        employeeDto.setEmployeeName("従業員NAME");
        employeeDto.setPassword("testPass");
        employeeDto.setRole("ROLE_ADMIN");
        EmployeeDto resultEmployeeDto = new EmployeeDto();
        resultEmployeeDto.setEmployeeId("100000000");
        resultEmployeeDto.setEmployeeName("従業員NAME");
        resultEmployeeDto.setPassword("testPassEncode");
        resultEmployeeDto.setRole("ROLE_ADMIN");

        // Mock設定
        when(passwordEncoder.encode("testPass")).thenReturn("testPassEncode");
        when(employeeDaoMock.insertOne(employeeDto)).thenReturn(0);

        // 実行
        Boolean actual = service.insertOne(employeeDto);

        // 検証
        verify(employeeDaoMock, times(1)).insertOne(employeeDtoCaptor.capture());
        assertEquals(resultEmployeeDto, employeeDtoCaptor.getValue());
        assertEquals(false, actual);
        assertNotNull(actual);

    }

    @Test
    @DisplayName("updateOne動作確認: 登録成功")
    void testUpdateOne1() {

        EmployeeServiceImpl service = initTestService();
        ArgumentCaptor<EmployeeDto> employeeDtoCaptor = ArgumentCaptor.forClass(EmployeeDto.class);

        // テストデータ設定
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId("100000000");
        employeeDto.setEmployeeName("従業員NAME");

        // Mock設定
        when(employeeDaoMock.updateOne(employeeDto)).thenReturn(1);

        // 実行
        Boolean actual = service.updateOne(employeeDto);

        // 検証
        verify(employeeDaoMock, times(1)).updateOne(employeeDtoCaptor.capture());
        assertEquals(employeeDto, employeeDtoCaptor.getValue());
        assertEquals(true, actual);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("updateOne動作確認: 更新失敗")
    void testUpdateOn2() {

        EmployeeServiceImpl service = initTestService();
        ArgumentCaptor<EmployeeDto> employeeDtoCaptor = ArgumentCaptor.forClass(EmployeeDto.class);

        // テストデータ設定
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployeeId("100000000");
        employeeDto.setEmployeeName("従業員NAME");

        // Mock設定
        when(employeeDaoMock.updateOne(employeeDto)).thenReturn(0);

        // 実行
        Boolean actual = service.updateOne(employeeDto);

        // 検証
        verify(employeeDaoMock, times(1)).updateOne(employeeDtoCaptor.capture());
        assertEquals(employeeDto, employeeDtoCaptor.getValue());
        assertEquals(false, actual);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("deleteOne動作確認: 削除成功")
    void testDeleteOne1() {

        EmployeeServiceImpl service = initTestService();
        ArgumentCaptor<String> employeeIdCaptor = ArgumentCaptor.forClass(String.class);

        // テストデータ設定
        String employeeId = "100000000";

        // Mock設定
        when(employeeDaoMock.deleteOne(employeeId)).thenReturn(1);

        // 実行
        Boolean actual = service.deleteOne(employeeId);

        // 検証
        verify(employeeDaoMock, times(1)).deleteOne(employeeIdCaptor.capture());
        assertEquals(employeeId, employeeIdCaptor.getValue());
        assertEquals(true, actual);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("deleteOne動作確認: 削除失敗")
    void testDeleteOne2() {

        EmployeeServiceImpl service = initTestService();
        ArgumentCaptor<String> employeeIdCaptor = ArgumentCaptor.forClass(String.class);

        // テストデータ設定
        String employeeId = "100000000";

        // Mock設定
        when(employeeDaoMock.deleteOne(employeeId)).thenReturn(0);

        // 実行
        Boolean actual = service.deleteOne(employeeId);

        // 検証
        verify(employeeDaoMock, times(1)).deleteOne(employeeIdCaptor.capture());
        assertEquals(employeeId, employeeIdCaptor.getValue());
        assertEquals(false, actual);
        assertNotNull(actual);
    }

    private EmployeeServiceImpl initTestService() {
        return new EmployeeServiceImpl(employeeDaoMock, employeeJdbcMock, passwordEncoder);
    }
}
