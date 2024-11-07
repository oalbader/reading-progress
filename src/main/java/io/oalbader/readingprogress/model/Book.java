package io.oalbader.readingprogress.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Integer totalPages;

    @Column(nullable = false)
    private Integer currentPage = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingSession> readingSessions = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt DESC")
    private List<Note> notes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column()
    private BookStatus status = BookStatus.NOT_STARTED;

    @Column(columnDefinition = "TEXT")
    private String review;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }

    public Integer getCurrentPage() { return currentPage; }
    public void setCurrentPage(Integer currentPage) { this.currentPage = currentPage; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<ReadingSession> getReadingSessions() { return readingSessions; }
    public void setReadingSessions(List<ReadingSession> readingSessions) { this.readingSessions = readingSessions; }

    public List<Note> getNotes() { return notes; }
    public void setNotes(List<Note> notes) { this.notes = notes; }

    public BookStatus getStatus() { 
        return status; 
    }

    public void setStatus(BookStatus status) { 
        this.status = status; 
    }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    // Helper method to add reading session
    public void addReadingSession(ReadingSession readingSession) {
        readingSessions.add(readingSession);
        readingSession.setBook(this);
    }

    // Add this method to calculate percentage
    public double getProgressPercentage() {
        if (totalPages == 0) return 0.0;
        return Math.round((currentPage * 100.0) / totalPages * 10.0) / 10.0;
    }
}
