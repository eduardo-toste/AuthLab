package com.auth.lab.repository;

import com.auth.lab.model.Note;
import com.auth.lab.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @EntityGraph(attributePaths = "author")
    List<Note> findAllByAuthor(User author);

    @EntityGraph(attributePaths = "author")
    Optional<Note> findByIdAndAuthor(Long noteId, User requester);

    @EntityGraph(attributePaths = "author")
    Optional<Note> findById(Long noteId);

}
