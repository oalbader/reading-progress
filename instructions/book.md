# Book Management Architecture

## Overview

After logging in, users can view a list of their books along with the status of each book and the last page they've read. Users can update the last page read for each book, and the system will maintain a history of these updates to track reading progress over time.

## Components

### Models

- **Book**
  - **Fields:**
    - `id` (Long): Unique identifier.
    - `title` (String): Title of the book.
    - `author` (String): Author of the book.
    - `totalPages` (Integer): Total number of pages in the book.
    - `currentPage` (Integer): Last page the user has read.
    - `user` (User): The owner of the book.
    - `readingSessions` (List\<ReadingSession\>): History of reading sessions.

- **ReadingSession**
  - **Fields:**
    - `id` (Long): Unique identifier.
    - `book` (Book): The book associated with the session.
    - `date` (LocalDateTime): Timestamp of the session.
    - `pagesRead` (Integer): Number of pages read in the session.

### Repositories

- **BookRepository**
  - Extends `JpaRepository<Book, Long>`.
  - **Methods:**
    - `List<Book> findByUser(User user)`: Retrieves books for a specific user.

- **ReadingSessionRepository**
  - Extends `JpaRepository<ReadingSession, Long>`.
  - **Methods:**
    - `List<ReadingSession> findByBook(Book book)`: Retrieves reading sessions for a specific book.

### Services

- **BookService**
  - **Methods:**
    - `List<Book> getAllBooksForUser(User user)`: Fetches all books for the logged-in user.
    - `Book updateCurrentPage(Long bookId, Integer newPage)`: Updates the current page of a book and records a new reading session.

- **ReadingSessionService**
  - **Methods:**
    - `List<ReadingSession> getReadingHistory(Long bookId)`: Retrieves the reading history for a book.

### Controllers

- **BookController**
  - **Endpoints:**
    - `GET /books`: Displays the list of user’s books with their status.
    - `POST /books/{id}/update`: Updates the current page of a specific book.

- **ReadingSessionController**
  - **Endpoints:**
    - `GET /books/{id}/history`: Displays the reading history for a specific book.

### User Interface

- **Books List Page (`books/list.html`)**
  - Displays all books owned by the user.
  - Shows the current status (e.g., "In Progress", "Completed") based on `currentPage` vs. `totalPages`.
  - Includes a form or button to update the last page read.

- **Reading History Page (`reading-sessions/list.html`)**
  - Lists all reading sessions for a selected book.
  - Displays the date and number of pages read in each session.

## Workflow

1. **Viewing Books**
   - User navigates to `/books`.
   - `BookController` fetches all books for the logged-in user via `BookService`.
   - The view displays each book’s title, author, current page, and status.

2. **Updating Book Progress**
   - User selects a book and submits an update for the current page.
   - `BookController` handles the update request, invoking `BookService` to update the book’s `currentPage`.
   - A new `ReadingSession` is created to log the update.

3. **Viewing Reading History**
   - User selects a book to view its history, navigating to `/books/{id}/history`.
   - `ReadingSessionController` retrieves all sessions for the book via `ReadingSessionService`.
   - The view displays a chronological list of reading sessions.

## Security

- Only authenticated users can access and modify their books and reading sessions.
- `SecurityConfig` ensures that endpoints under `/books/**` are secured and accessible only to the respective owners.

## Database Schema

- **User**
  - Existing as per current codebase.

- **Book**
  - Many-to-One relationship with `User`.
  - One-to-Many relationship with `ReadingSession`.

- **ReadingSession**
  - Many-to-One relationship with `Book`.

## Technologies

- **Backend:** Spring Boot, Spring Security, Spring Data JPA
- **Frontend:** Thymeleaf, HTMX
- **Database:** H2 (in-memory for development)
- **Build Tool:** Gradle

## Implementation Notes

- Ensure that `Book` and `ReadingSession` entities have proper mappings and cascading where necessary.
- Utilize HTMX for dynamic updates to the UI without full page reloads when updating book progress.
- Validate user inputs to prevent invalid page numbers or unauthorized access.
- Implement pagination on the reading history page if there are many sessions.
