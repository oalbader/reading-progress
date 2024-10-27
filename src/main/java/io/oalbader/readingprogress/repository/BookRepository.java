package io.oalbader.readingprogress.repository;

import io.oalbader.readingprogress.model.Book;
import io.oalbader.readingprogress.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByUser(User user);
}
