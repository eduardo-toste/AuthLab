package com.auth.lab.dto;

public record LoginResponse(

        String token,
        long expiresIn

) {
}
