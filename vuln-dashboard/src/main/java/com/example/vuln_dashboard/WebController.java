package com.example.vuln_dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Use @Controller (NOT @RestController) for HTML pages
public class WebController {

    private final VulnerabilityRepository repository;
    private final GlobalNoteRepository noteRepository;

    public WebController(VulnerabilityRepository repository, GlobalNoteRepository noteRepository) {
        this.repository = repository;
        this.noteRepository = noteRepository;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard"; // Automatically moves users from :8080 to :8080/dashboard
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Points to login.html
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        // This sends the list of vulnerabilities to the HTML page
        model.addAttribute("vulnerabilities", repository.findAll());
        model.addAttribute("notes", noteRepository.findAll());
        return "index"; // This looks for src/main/resources/templates/index.html
    }
}