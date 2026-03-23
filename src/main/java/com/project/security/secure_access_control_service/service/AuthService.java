package com.project.security.secure_access_control_service.service;

import com.project.security.secure_access_control_service.dto.*;
import com.project.security.secure_access_control_service.entity.User;
import com.project.security.secure_access_control_service.repository.UserRepository;
import com.project.security.secure_access_control_service.security.jwt.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(AuthRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRoles(Set.of("ROLE_USER"));
        repo.save(user);
        return "Registered";
    }

    public AuthResponse login(AuthRequest request) {
        User user = repo.findByUsername(request.getUsername()).orElseThrow();

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new AuthResponse(jwtUtil.generateToken(user.getUsername()));
    }
}