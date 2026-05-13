package com.beej.auth.service;

import com.beej.auth.dto.AuthResponseDTO;
import com.beej.auth.dto.LoginRequestDTO;
import com.beej.auth.dto.RegisterRequestDTO;
import com.beej.auth.entity.User;
import com.beej.auth.mapper.AuthMapper;
import com.beej.auth.repository.UserRepository;
import com.beej.common.enums.RoleType;
import com.beej.exception.BadRequestException;
import com.beej.exception.ResourceNotFoundException;
import com.beej.exception.UnauthorizedException;
import com.beej.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final AuthMapper authMapper;

    @Override
    @Transactional
    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {

        System.out.println("===== REGISTER API STARTED =====");

        System.out.println("Username: " + registerRequest.getUsername());
        System.out.println("Email: " + registerRequest.getEmail());

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            System.out.println("Username already exists");
            throw new BadRequestException("Username is already taken");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            System.out.println("Email already exists");
            throw new BadRequestException("Email is already registered");
        }

        System.out.println("Creating User Object");

        User user = authMapper.toUser(registerRequest);

        System.out.println("User mapped successfully");

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        System.out.println("Password encoded");

        user.setRole(RoleType.USER);
        System.out.println("Role set: " + user.getRole());

        user.setVerificationToken(UUID.randomUUID().toString());
        System.out.println("Verification token generated");

        System.out.println("Saving user to database...");

        User savedUser = userRepository.save(user);

        System.out.println("User saved successfully");
        System.out.println("Saved User ID: " + savedUser.getId());
        System.out.println("Saved Username: " + savedUser.getUsername());
        System.out.println("Saved Role: " + savedUser.getRole());

        try {

            System.out.println("Getting authorities...");
            System.out.println("Authorities: " + savedUser.getAuthorities());

            System.out.println("Creating Authentication object...");

            Authentication auth = new UsernamePasswordAuthenticationToken(
                    savedUser,
                    null,
                    savedUser.getAuthorities()
            );

            System.out.println("Authentication object created");

            System.out.println("Generating JWT token...");

            String token = tokenProvider.generateToken(auth);

            System.out.println("JWT Token Generated Successfully");

            System.out.println("Creating AuthResponseDTO");

            AuthResponseDTO response =
                    authMapper.toAuthResponse(savedUser, token);

            System.out.println("Response created successfully");

            System.out.println("===== REGISTER API COMPLETED =====");

            return response;

        } catch (Exception e) {

            System.out.println("===== ERROR OCCURRED =====");

            e.printStackTrace();

            throw e;
        }
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {

        System.out.println(passwordEncoder.encode("Admin@123"));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        return authMapper.toAuthResponse(user, token);
    }

    @Override
    public AuthResponseDTO refreshToken(String token) {

        if (!tokenProvider.validateToken(token)) {
            throw new UnauthorizedException("Invalid token");
        }

        String username = tokenProvider.getUsernameFromToken(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );

        String newToken = tokenProvider.generateToken(auth);

        return authMapper.toAuthResponse(user, newToken);
    }
    @Override
    public void logout(String token) {
        // TODO: Implement token blacklisting if needed
        SecurityContextHolder.clearContext();
    }

    @Override
    @Transactional
    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid verification token"));

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(24));
        userRepository.save(user);

        // TODO: Send password reset email
        // emailService.sendPasswordResetEmail(user.getEmail(), resetToken);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByValidResetToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid or expired reset token"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }
}
