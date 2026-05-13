package com.beej.admin.dto;

import com.beej.common.enums.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserManagementDTO {
    
    private Long id;
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    
    private String firstName;
    private String lastName;
    private String phoneNumber;
    
    @NotBlank(message = "Role is required")
    private RoleType role;
    
    private Boolean enabled;
    private Boolean emailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private Integer totalOrders;
    private BigDecimal totalSpent;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserUpdateRequest {
        @Size(max = 50)
        private String firstName;
        
        @Size(max = 50)
        private String lastName;
        
        @Size(max = 20)
        private String phoneNumber;
        
        private RoleType role;
        private Boolean enabled;
    }
}
