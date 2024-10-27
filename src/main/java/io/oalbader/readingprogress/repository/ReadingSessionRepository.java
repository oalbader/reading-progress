package io.oalbader.readingprogress.repository;

import io.oalbader.readingprogress.model.Book;
import io.oalbader.readingprogress.model.ReadingSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingSessionRepository extends JpaRepository<ReadingSession, Long> {
    List<ReadingSession> findByBookOrderByDateDesc(Book book);
}
