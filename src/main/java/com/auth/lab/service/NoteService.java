package com.auth.lab.service;

import com.auth.lab.dto.CreateNoteRequest;
import com.auth.lab.dto.NoteResponse;
import com.auth.lab.exception.NoteNotFoundException;
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
        List<Note> authorNotes = isRequesterAdmin(author)
                ? noteRepository.findAll()
                : noteRepository.findAllByAuthor(author);

        return authorNotes.stream().map(noteMapper::toResponse).toList();
    }

    public NoteResponse getNoteById(Long noteId, User author) {
        Note authorNote = isRequesterAdmin(author)
                ? noteRepository.findById(noteId)
                  .orElseThrow(NoteNotFoundException::new)
                : noteRepository.findByIdAndAuthor(noteId, author)
                  .orElseThrow(NoteNotFoundException::new);

        return noteMapper.toResponse(authorNote);
    }

    private boolean isRequesterAdmin(User requester) {
        return requester.hasRole("ROLE_ADMIN");
    }
}
