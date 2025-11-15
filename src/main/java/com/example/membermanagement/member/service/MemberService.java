package com.example.membermanagement.member.service;

import com.example.membermanagement.member.exception.CommonException;
import com.example.membermanagement.member.exception.ErrorCode;
import com.example.membermanagement.member.model.Member;
import com.example.membermanagement.member.model.MemberEntity;
import com.example.membermanagement.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    // 로그인
    public String login(String username, String password) {
        Member member = loadMemberByUsername(username);
        if(!member.password().equals(password)) {
            throw new CommonException(ErrorCode.INVALID_PASSWORD);
        }
        return "success";
    }

    // 유저 정보 단건 조회
    @Transactional(readOnly = true)
    public Member loadMemberByUsername(String username) {
        return memberRepository.findByUsername(username).map(Member::fromEntity).orElseThrow(
                () -> new CommonException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username))
        );
    }

    // 멤버 전체 조회
    @Transactional(readOnly = true)
    public List<Member> loadAllMembers() {
        return memberRepository.findAll().stream().map(Member::fromEntity).toList();
    }

    private void checkUsernameExistenceOrException(String username) {
        memberRepository.findByUsername(username).ifPresent(it -> {
            throw new CommonException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", username));
        });
    }
}
