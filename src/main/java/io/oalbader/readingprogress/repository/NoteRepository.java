package io.oalbader.readingprogress.repository;

import io.oalbader.readingprogress.model.Note;
import io.oalbader.readingprogress.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByBookOrderByCreatedAtDesc(Book book);
} 