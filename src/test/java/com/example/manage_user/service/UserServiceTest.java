package com.example.manage_user.service;

import com.example.manage_user.domain.model.User;
import com.example.manage_user.dto.UserResponseDTO;
import com.example.manage_user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService service;

    @Test
    void findAll_returnsPaginatedUsers() {
        Pageable pageable = PageRequest.of(0, 5);

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Alice");
        user.setUsername("alice");
        user.setEmail("alice@example.com");
        user.setPassword("encoded");

        Page<User> repoPage = new PageImpl<>(List.of(user), pageable, 1);
        when(repository.findAll(pageable)).thenReturn(repoPage);

        Page<UserResponseDTO> result = service.findAll(pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getContent().get(0).email()).isEqualTo("alice@example.com");
    }
}
