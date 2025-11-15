package com.example.membermanagement.member.service;

import com.example.membermanagement.member.exception.CommonException;
import com.example.membermanagement.member.exception.ErrorCode;
import com.example.membermanagement.member.model.Member;
import com.example.membermanagement.member.model.MemberEntity;
import com.example.membermanagement.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public Member join(String username, String password, String email) {
        checkUsernameExistenceOrException(username); // 이미 존재하는 username인지 체크
        MemberEntity memberEntity = MemberEntity.of(username, password, email);
        // TODO 비밀번호 암호화
        return Member.fromEntity(memberRepository.save(memberEntity));
    }

    private void checkUsernameExistenceOrException(String username) {
        memberRepository.findByUsername(username).ifPresent(it -> {
            throw new CommonException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", username));
        });
    }
}
