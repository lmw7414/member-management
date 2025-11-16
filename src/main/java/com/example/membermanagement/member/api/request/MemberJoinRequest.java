package com.example.membermanagement.member.api.request;

public record MemberJoinRequest(
        String username,
        String password,
        String email
) {
}
