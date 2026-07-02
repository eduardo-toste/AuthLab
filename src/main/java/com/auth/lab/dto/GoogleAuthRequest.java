package com.auth.lab.dto;

import jakarta.validation.constraints.NotBlank;

public record GoogleAuthRequest(

        @NotBlank(message = "Token must not be null")
        String idToken

) {
}
