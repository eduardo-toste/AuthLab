package com.auth.lab.service;

import com.auth.lab.configuration.GoogleTokenVerifier;
import com.auth.lab.configuration.TokenProvider;
import com.auth.lab.dto.GoogleAuthRequest;
import com.auth.lab.dto.LoginRequest;
import com.auth.lab.dto.LoginResponse;
import com.auth.lab.dto.RegisterRequest;
import com.auth.lab.enums.AuthProvider;
import com.auth.lab.exception.RoleNotFoundException;
import com.auth.lab.exception.UserAlreadyExistsException;
import com.auth.lab.model.Role;
import com.auth.lab.model.User;
import com.auth.lab.repository.RoleRepository;
import com.auth.lab.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final GoogleTokenVerifier googleTokenVerifier;

    @Value("${jwt.expiration.time}")
    private long expirationTime;

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

    public LoginResponse login(LoginRequest request) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            String token = tokenProvider.generateToken(authentication);
            return new LoginResponse(token, expirationTime);
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Invalid credentials.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public LoginResponse loginWithGoogle(GoogleAuthRequest request) throws Exception {
        GoogleIdToken.Payload payload = googleTokenVerifier.verify(request.idToken());

        String email = payload.getEmail();
        String googleId = payload.getSubject();
        String name = (String) payload.get("name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> registerGoogleUser(email, name, googleId));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());

        String token = tokenProvider.generateToken(authentication);
        return new LoginResponse(token, expirationTime);
    }

    private User registerGoogleUser(String email, String name, String googleId) {
        Role defaultRole = loadDefaultRole();

        User newUser = User.builder()
                .name(name)
                .email(email)
                .googleId(googleId)
                .provider(AuthProvider.GOOGLE)
                .roles(Set.of(defaultRole))
                .build();

        return userRepository.save(newUser);
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
