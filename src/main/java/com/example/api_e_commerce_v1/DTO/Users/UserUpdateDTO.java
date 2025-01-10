package com.example.api_e_commerce_v1.DTO.Users;

import com.example.api_e_commerce_v1.Enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String avatar;
    private UserStatus status;
}
