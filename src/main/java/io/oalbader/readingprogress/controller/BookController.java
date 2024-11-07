package io.oalbader.readingprogress.controller;

import io.oalbader.readingprogress.model.Book;
import io.oalbader.readingprogress.model.User;
import io.oalbader.readingprogress.model.BookStatus;
import io.oalbader.readingprogress.service.BookService;
import io.oalbader.readingprogress.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final NoteService noteService;

    @Autowired
    public BookController(BookService bookService, NoteService noteService) {
        this.bookService = bookService;
        this.noteService = noteService;
    }

    @GetMapping
    public String listBooks(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("books", bookService.getAllBooksForUser(user));
        return "books/list";
    }

    @PostMapping("/{id}/update")
    public String updateProgress(@PathVariable Long id, @RequestParam int newPage, Model model) {
        Book book = bookService.updateProgress(id, newPage);
        model.addAttribute("book", book);
        return "books/fragments/book-item :: book-item";
    }

    @GetMapping("/add")
    public String showAddBookForm() {
        return "books/add";
    }

    @PostMapping("/add")
    public String addBook(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam Integer totalPages,
            @AuthenticationPrincipal User user,  // Make sure this is your custom User class
            Model model) {
        // Add debug logging if needed
        if (user == null) {
            throw new RuntimeException("User not authenticated");
        }
        try {
            bookService.createBook(title, author, totalPages, user);
            return "redirect:/books";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("title", title);
            model.addAttribute("author", author);
            model.addAttribute("totalPages", totalPages);
            return "books/add";
        }
    }

    @PostMapping("/{id}/review")
    public String updateReview(@PathVariable Long id, @RequestParam String review, Model model) {
        Book book = bookService.updateReview(id, review);
        model.addAttribute("book", book);
        model.addAttribute("reviewSaved", true);
        return "books/details :: #review-section";
    }

    @GetMapping("/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("readingSessions", book.getReadingSessions());
        return "books/details";
    }

    @PostMapping("/{id}/notes")
    public String addNote(@PathVariable Long id, 
                         @RequestParam String content,
                         @RequestParam(required = false) MultipartFile image,
                         Model model) throws IOException {
        Book book = bookService.getBookById(id);
        noteService.addNote(book, content, image);
        model.addAttribute("book", book);
        model.addAttribute("notes", book.getNotes());
        model.addAttribute("noteSaved", true);
        return "books/details :: #notes-section";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("HX-Redirect", "/books");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
