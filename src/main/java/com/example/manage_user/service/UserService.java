package com.example.manage_user.service;

import com.example.manage_user.domain.model.User;
import com.example.manage_user.dto.UserRequestDTO;
import com.example.manage_user.dto.UserResponseDTO;
import com.example.manage_user.exception.ResourceNotFoundException;
import com.example.manage_user.mapper.UserMapper;
import com.example.manage_user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO create(UserRequestDTO dto) {
        User user = UserMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = repository.save(user);
        return UserMapper.toDTO(saved);
    }

    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(UserMapper::toDTO);
    }

    public UserResponseDTO findById(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserMapper.toDTO(user);
    }

    public void delete(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        repository.delete(user);
    }
}
