package com.example.paymentservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotNull(message = "email cannot be blank")
    private String email;

    @NotNull(message = "phoneNumber cannot be blank")
    private String phoneNumber;

    @NotNull(message = "userID cannot be null")
    private Long userId;
}
