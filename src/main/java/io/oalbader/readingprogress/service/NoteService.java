package io.oalbader.readingprogress.service;

import io.oalbader.readingprogress.model.Note;
import io.oalbader.readingprogress.model.Book;
import io.oalbader.readingprogress.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note addNote(Book book, String content, MultipartFile image) throws IOException {
        Note note = new Note();
        note.setBook(book);
        note.setContent(content);
        
        if (image != null && !image.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);
            
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath);
            note.setImagePath(fileName);
        }
        
        return noteRepository.save(note);
    }
} 