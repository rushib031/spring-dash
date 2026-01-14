package com.example.vuln_dashboard;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final VulnerabilityRepository repository;

    public DataLoader(VulnerabilityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Checking if data already exists to avoid duplicates
        if (repository.count() == 0) {
            repository.save(new Vulnerability(null, "CVE-2024-001", "Broken Authentication", "Critical"));
            repository.save(new Vulnerability(null, "CVE-2023-999", "Insecure Direct Object Reference", "High"));
            repository.save(new Vulnerability(null, "CVE-2022-123", "Sensitive Data Exposure", "Medium"));
            
            System.out.println("Sample vulnerability data loaded into H2 database.");
        }
    }
}