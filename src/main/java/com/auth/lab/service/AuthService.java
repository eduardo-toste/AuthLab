package com.auth.lab.service;

import com.auth.lab.dto.RegisterRequest;
import com.auth.lab.exception.RoleNotFoundException;
import com.auth.lab.exception.UserAlreadyExistsException;
import com.auth.lab.model.Role;
import com.auth.lab.model.User;
import com.auth.lab.repository.RoleRepository;
import com.auth.lab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(RegisterRequest request) {
        checkIfUserAlreadyExists(request.email());
        Role defaultRole = loadDefaultRole();

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(defaultRole))
                .build();

        userRepository.save(user);
    }

    private Role loadDefaultRole() {
        return roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(RoleNotFoundException::new);
    }

    private void checkIfUserAlreadyExists(String username) {
        userRepository
                .findByEmail(username)
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException();
                });
    }

}
