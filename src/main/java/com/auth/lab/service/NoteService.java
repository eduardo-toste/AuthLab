package com.auth.lab.service;

import com.auth.lab.dto.CreateNoteRequest;
import com.auth.lab.dto.NoteResponse;
import com.auth.lab.mapper.NoteMapper;
import com.auth.lab.model.Note;
import com.auth.lab.model.User;
import com.auth.lab.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public void createNote(CreateNoteRequest request, User author) {
        Note note = Note.builder()
                .title(request.title())
                .content(request.content())
                .author(author)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        noteRepository.save(note);
    }

    public List<NoteResponse> getNotes(User author) {
        List<Note> authorNotes = noteRepository.findAllByAuthor(author);
        return authorNotes.stream().map(noteMapper::toResponse).toList();
    }

    // TO-DO: finish this implementation
    public NoteResponse getNoteById(Long noteId) {
        return null;
    }
}
