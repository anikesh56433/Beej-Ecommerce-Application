package com.beej.auth.mapper;

import com.beej.auth.dto.AuthResponseDTO;
import com.beej.auth.dto.RegisterRequestDTO;
import com.beej.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public User toUser(RegisterRequestDTO registerRequest) {
        if (registerRequest == null) {
            return null;
        }

        return User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .phoneNumber(registerRequest.getPhoneNumber())
                .build();
    }

    public AuthResponseDTO toAuthResponse(User user, String token) {
        if (user == null) {
            return null;
        }

        return AuthResponseDTO.builder()
                .token(token)
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .build();
    }
}
