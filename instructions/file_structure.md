# Reading Progress Tracker - File Structure
src
├── main
│ ├── java
│ │ └── io
│ │ └── oalbader
│ │ └── readingprogress
│ │ ├── ReadingProgressApplication.java
│ │ ├── config
│ │ │ └── SecurityConfig.java
│ │ ├── controller
│ │ │ ├── AuthController.java
│ │ │ ├── BookController.java
│ │ │ └── ReadingSessionController.java
│ │ ├── model
│ │ │ ├── Book.java
│ │ │ ├── ReadingSession.java
│ │ │ └── User.java
│ │ ├── repository
│ │ │ ├── BookRepository.java
│ │ │ ├── ReadingSessionRepository.java
│ │ │ └── UserRepository.java
│ │ └── service
│ │ ├── BookService.java
│ │ ├── ReadingSessionService.java
│ │ └── UserService.java
│ └── resources
│ ├── application.properties
│ ├── static
│ │ ├── css
│ │ │ └── styles.css
│ │ └── js
│ │ └── app.js
│ └── templates
│ ├── auth
│ │ ├── login.html
│ │ └── register.html
│ ├── books
│ │ ├── add.html
│ │ └── list.html
│ ├── fragments
│ │ ├── header.html
│ │ └── footer.html
│ ├── index.html
│ └── reading-sessions
│ ├── add.html
│ └── list.html
└── test
└── java
└── io
└── oalbader
└── readingprogress
└── ReadingProgressApplicationTests.java
.
This file structure provides a solid foundation for your reading progress tracking application using Spring Boot, Thymeleaf, and HTMX. It separates concerns, follows Spring Boot conventions, and organizes the code in a maintainable way.
This file structure organizes your Spring Boot application for the reading progress tracker. Here's a brief explanation of each component:

1. `ReadingProgressApplication.java`: Main application class.
2. `config/SecurityConfig.java`: Security configuration for user authentication.
3. `controller/`: Contains controllers for handling HTTP requests.
4. `model/`: Defines entity classes (Book, ReadingSession, User).
5. `repository/`: Interfaces for database operations.
6. `service/`: Business logic layer.
7. `resources/`:
   - `application.properties`: Application configuration.
   - `static/`: Static resources (CSS, JavaScript).
   - `templates/`: Thymeleaf templates for views.

This structure supports the following features:
- User registration and login
- Adding and listing books
- Recording and viewing reading sessions
- Calculating pages read per session

The Thymeleaf templates will integrate with HTMX for enhanced interactivity. The `SecurityConfig` will handle user authentication and authorization.

To implement this structure, create the necessary Java classes, interfaces, and HTML templates as outlined above. Then, implement the required logic in each component to achieve the desired functionality.