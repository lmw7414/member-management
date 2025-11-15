package com.example.membermanagement.member.service;

import com.example.membermanagement.member.exception.CommonException;
import com.example.membermanagement.member.exception.ErrorCode;
import com.example.membermanagement.member.model.Member;
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
        // mocking
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(memberRepository.save(any())).thenReturn(fixture(username, password, email));

        assertDoesNotThrow(() -> sut.join(username, password, email));
        verify(memberRepository, times(1)).findByUsername(eq(username));
    }

    @Test
    void 회원가입시_이미_존재하는_username이라면_4XX에러반환() {
        String username = "test-username";
        String password = "test-password";
        String email = "test-email";
        // mocking
        when(memberRepository.findByUsername(anyString())).thenReturn(Optional.of(fixture(username, password, email)));

        CommonException e = assertThrows(CommonException.class, () -> sut.join(username, password, email));
        assertEquals(ErrorCode.DUPLICATED_USER_NAME, e.getErrorCode());
    }


    private MemberEntity fixture(String username, String password, String email) {
        return MemberEntity.of(username, password, email);
    }

}