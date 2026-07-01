package com.auth.lab.mapper;

import com.auth.lab.dto.NoteResponse;
import com.auth.lab.model.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

    public NoteResponse toResponse(Note note) {
        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getAuthor().getId(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }

}
