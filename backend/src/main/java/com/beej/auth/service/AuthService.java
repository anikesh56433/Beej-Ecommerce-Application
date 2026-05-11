package com.beej.auth.service;

import com.beej.auth.dto.AuthResponseDTO;
import com.beej.auth.dto.LoginRequestDTO;
import com.beej.auth.dto.RegisterRequestDTO;

public interface AuthService {
    
    AuthResponseDTO register(RegisterRequestDTO registerRequest);
    
    AuthResponseDTO login(LoginRequestDTO loginRequest);
    
    AuthResponseDTO refreshToken(String token);
    
    void logout(String token);
    
    void verifyEmail(String token);
    
    void forgotPassword(String email);
    
    void resetPassword(String token, String newPassword);
}
