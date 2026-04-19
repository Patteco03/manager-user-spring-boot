package com.example.manage_user.dto;

import java.util.UUID;

import java.time.LocalDateTime;

public record UserResponseDTO(UUID id, String name, String username, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
