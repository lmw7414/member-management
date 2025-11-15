package com.example.membermanagement.member.model;

import java.time.LocalDateTime;

public record Member(
        String username,
        String password,
        String email,
        LocalDateTime createdAt
) {

    public static Member fromEntity(MemberEntity entity) {
        return new Member(entity.getUsername(), entity.getPassword(), entity.getEmail(), entity.getCreatedAt());
    }
}
