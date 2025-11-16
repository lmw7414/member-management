package com.example.membermanagement.member.api.request;

public record MemberLoginRequest(
        String username,
        String password
) {
}
