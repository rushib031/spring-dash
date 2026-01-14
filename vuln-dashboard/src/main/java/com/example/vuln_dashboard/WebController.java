package com.example.vuln_dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Use @Controller (NOT @RestController) for HTML pages
public class WebController {

    private final VulnerabilityRepository repository;

    public WebController(VulnerabilityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        // This sends the list of vulnerabilities to the HTML page
        model.addAttribute("vulnerabilities", repository.findAll());
        return "index"; // This looks for src/main/resources/templates/index.html
    }
}