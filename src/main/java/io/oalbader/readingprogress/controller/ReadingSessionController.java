package io.oalbader.readingprogress.controller;

import io.oalbader.readingprogress.model.Book;
import io.oalbader.readingprogress.model.ReadingSession;
import io.oalbader.readingprogress.service.BookService;
import io.oalbader.readingprogress.service.ReadingSessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ReadingSessionController {
    private final ReadingSessionService readingSessionService;
    private final BookService bookService;

    public ReadingSessionController(ReadingSessionService readingSessionService, BookService bookService) {
        this.readingSessionService = readingSessionService;
        this.bookService = bookService;
    }

    @GetMapping("/books/{id}/history")
    public String viewHistory(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        List<ReadingSession> sessions = readingSessionService.getReadingHistory(id);
        
        model.addAttribute("book", book);
        model.addAttribute("sessions", sessions);
        return "reading-sessions/list";
    }
}
