package com.auth.lab.dto;

import java.time.LocalDateTime;

public record NoteResponse(

       Long id,
       String title,
       String content,
       Long authorId,
       LocalDateTime createdAt,
       LocalDateTime updatedAt

) {
}
