package com.example.manage_user.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UserResponseDTO {
    private UUID id;
    private String name;
    private String email;

    public UserResponseDTO(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
