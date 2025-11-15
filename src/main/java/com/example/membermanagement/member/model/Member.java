package com.example.membermanagement.member.model;

import java.time.LocalDateTime;

public record Member(
        String username,
        String email,
        LocalDateTime createdAt
) {

    public static Member fromEntity(MemberEntity entity) {
        return new Member(entity.getUsername(), entity.getEmail(), entity.getCreatedAt());
    }
}
