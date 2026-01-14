package com.example.vuln_dashboard;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final GlobalNoteRepository repository;

    public NoteController(GlobalNoteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public GlobalNote createNote(@RequestBody GlobalNote note) {
        note.setCreatedAt(LocalDateTime.now());
        return repository.save(note);
    }
}
