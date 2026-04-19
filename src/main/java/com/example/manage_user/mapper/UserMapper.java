package com.example.manage_user.mapper;

import com.example.manage_user.domain.model.User;
import com.example.manage_user.dto.UserRequestDTO;
import com.example.manage_user.dto.UserResponseDTO;

public class UserMapper {
    public static User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
