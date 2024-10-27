package io.oalbader.readingprogress.controller;

import io.oalbader.readingprogress.model.Book;
import io.oalbader.readingprogress.model.User;
import io.oalbader.readingprogress.model.BookStatus;
import io.oalbader.readingprogress.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
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
}
