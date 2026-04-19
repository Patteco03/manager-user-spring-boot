package com.example.manage_user.security.auth;

import com.example.manage_user.domain.model.User;
import com.example.manage_user.dto.LoginRequestDTO;
import com.example.manage_user.dto.UserResponseDTO;
import com.example.manage_user.exception.BusinessException;
import com.example.manage_user.exception.ErrorCode;
import com.example.manage_user.mapper.UserMapper;
import com.example.manage_user.repository.UserRepository;
import com.example.manage_user.security.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository repository, PasswordEncoder encoder, JwtService jwtService) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public String login(LoginRequestDTO dto) {

        User user = repository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND ,"Invalid credentials"));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "Invalid credentials");
        }

        return jwtService.generateToken(user.getEmail());
    }

    public UserResponseDTO me(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, "User not found"));
        return UserMapper.toDTO(user);
    }
}
