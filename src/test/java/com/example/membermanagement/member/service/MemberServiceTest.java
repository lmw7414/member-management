package com.example.membermanagement.member.service;

import com.example.membermanagement.member.exception.CommonException;
import com.example.membermanagement.member.exception.ErrorCode;
import com.example.membermanagement.member.model.MemberEntity;
import com.example.membermanagement.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Import(MemberService.class)
class MemberServiceTest {

    @Autowired
    private MemberService sut;
    @MockitoBean
    private MemberRepository memberRepository;

    @Test
    void 회원가입시_정상동작() {
        String username = "test-username";
        String password = "test-password";
        String email = "test-email";
        MemberEntity fixture = createMember(username, password, email);

        // mocking
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(memberRepository.save(any())).thenReturn(fixture);

        assertDoesNotThrow(() -> sut.join(username, password, email));
        verify(memberRepository, times(1)).findByUsername(eq(username));
    }

    @Test
    void 회원가입시_이미_존재하는_username이라면_4XX에러반환() {
        String username = "test-username";
        String password = "test-password";
        String email = "test-email";
        MemberEntity fixture = createMember(username, password, email);

        // mocking
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.of(fixture));

        CommonException e = assertThrows(CommonException.class, () -> sut.join(username, password, email));
        assertEquals(ErrorCode.DUPLICATED_USER_NAME, e.getErrorCode());
    }

    @Test
    void 로그인시_정상동작() {
        String username = "test-username";
        String password = "test-password";
        String email = "test-email";
        MemberEntity fixture = createMember(username, password, email);

        //mocking
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.of(fixture));

        assertDoesNotThrow(() -> sut.login(username, password));
    }

    @Test
    void 로그인시_해당username으로_가입한_적이_없는_경우_4XX에러반환() {
        String username = "test-username";
        String password = "test-password";

        //mocking
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        CommonException e = assertThrows(CommonException.class, () -> sut.login(username, password));
        assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 로그인시_비밀번호가_틀린_경우_4XX에러반환() {
        String username = "test-username";
        String password = "test-password";
        String wrongPassword = "wrong-password";
        String email = "test-email";
        MemberEntity fixture = createMember(username, password, email);
        //mocking
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.of(fixture));

        CommonException e = assertThrows(CommonException.class, () -> sut.login(username, wrongPassword));
        assertEquals(ErrorCode.INVALID_PASSWORD, e.getErrorCode());
    }

    private MemberEntity createMember(String username, String password, String email) {
        return MemberEntity.of(username, password, email);
    }

}