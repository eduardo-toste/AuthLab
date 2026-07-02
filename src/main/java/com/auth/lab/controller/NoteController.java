package com.auth.lab.controller;

import com.auth.lab.dto.CreateNoteRequest;
import com.auth.lab.dto.NoteResponse;
import com.auth.lab.model.Note;
import com.auth.lab.model.User;
import com.auth.lab.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CreateNoteRequest request,
                                       @AuthenticationPrincipal User author) {
        noteService.createNote(request, author);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getNotes(@AuthenticationPrincipal User author) {
        List<NoteResponse> response = noteService.getNotes(author);
        return ResponseEntity.ok(response);
    }

    // @GetMapping("/{noteId}/user/"{userId}")
    // TO-DO: finish this implementation with correct permissions
    @GetMapping("/{noteId}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long noteId,
                                                    @AuthenticationPrincipal User author) {
        NoteResponse response = noteService.getNoteById(noteId, author);
        return ResponseEntity.ok(response);
    }

}
