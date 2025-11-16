package com.example.membermanagement.member.api.response;

import com.example.membermanagement.member.model.Member;

import java.time.LocalDateTime;

public record MemberResponse(
        String username,
        String email,
        LocalDateTime createdAt
) {

    public static MemberResponse fromMember(Member member) {
        return new MemberResponse(member.username(), member.email(), member.createdAt());
    }
}
