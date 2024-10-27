package io.oalbader.readingprogress.service;

import io.oalbader.readingprogress.model.Book;
import io.oalbader.readingprogress.model.ReadingSession;
import io.oalbader.readingprogress.repository.BookRepository;
import io.oalbader.readingprogress.repository.ReadingSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadingSessionService {
    private final ReadingSessionRepository readingSessionRepository;
    private final BookRepository bookRepository;

    public ReadingSessionService(ReadingSessionRepository readingSessionRepository, BookRepository bookRepository) {
        this.readingSessionRepository = readingSessionRepository;
        this.bookRepository = bookRepository;
    }

    public List<ReadingSession> getReadingHistory(Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        return readingSessionRepository.findByBookOrderByDateDesc(book);
    }
}
