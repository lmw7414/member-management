package com.example.membermanagement.member.api.response;

import com.example.membermanagement.member.model.Member;

public record MemberJoinResponse(
        String username,
        String email
) {
    public static MemberJoinResponse fromMember(Member member) {
        return new MemberJoinResponse(member.username(), member.email());
    }

}
