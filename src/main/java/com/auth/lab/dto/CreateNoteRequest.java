package com.auth.lab.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateNoteRequest(

        @NotBlank(message = "Title must not be null.")
        String title,

        @NotBlank(message = "Content must not be null.")
        String content

) {
}
