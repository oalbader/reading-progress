package io.oalbader.readingprogress.service;

import io.oalbader.readingprogress.model.Book;
import io.oalbader.readingprogress.model.BookStatus;
import io.oalbader.readingprogress.model.ReadingSession;
import io.oalbader.readingprogress.model.User;
import io.oalbader.readingprogress.repository.BookRepository;
import io.oalbader.readingprogress.repository.ReadingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final ReadingSessionRepository readingSessionRepository;

    @Autowired
    public BookService(BookRepository bookRepository, ReadingSessionRepository readingSessionRepository) {
        this.bookRepository = bookRepository;
        this.readingSessionRepository = readingSessionRepository;
    }

    public List<Book> getAllBooksForUser(User user) {
        return bookRepository.findByUser(user);
    }

    @Transactional
    public Book updateProgress(Long bookId, int newPage) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));

        // Validate new page
        if (newPage < 0 || newPage > book.getTotalPages()) {
            throw new RuntimeException("Invalid page number");
        }

        // Calculate pages read in this session
        int pagesRead = newPage - book.getCurrentPage();
        
        // Only create a session if pages were actually read
        if (pagesRead > 0) {
            ReadingSession readingSession = new ReadingSession();
            readingSession.setBook(book);
            readingSession.setPagesRead(pagesRead);
            readingSessionRepository.save(readingSession);
        }
        
        // Update book status
        if (newPage == book.getTotalPages()) {
            book.setStatus(BookStatus.COMPLETED);
        } else if (newPage > 0) {
            book.setStatus(BookStatus.IN_PROGRESS);
        }
        
        book.setCurrentPage(newPage);
        return bookRepository.save(book);
    }

    @Transactional
    public Book createBook(String title, String author, Integer totalPages, User user) {
        if (title == null || title.trim().isEmpty()) {
            throw new RuntimeException("Title is required");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new RuntimeException("Author is required");
        }
        if (totalPages == null || totalPages <= 0) {
            throw new RuntimeException("Total pages must be greater than 0");
        }

        Book book = new Book();
        book.setTitle(title.trim());
        book.setAuthor(author.trim());
        book.setTotalPages(totalPages);
        book.setCurrentPage(0);
        book.setUser(user);

        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateReview(Long bookId, String review) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        
        book.setReview(review);
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
        bookRepository.delete(book);
    }
}
