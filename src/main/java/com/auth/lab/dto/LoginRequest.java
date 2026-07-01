package com.auth.lab.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "E-mail must not be blank")
        String email,

        @NotBlank(message = "Password must not be blank")
        String password

) {
}
