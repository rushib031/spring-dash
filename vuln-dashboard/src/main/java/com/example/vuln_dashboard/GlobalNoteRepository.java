package com.example.vuln_dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalNoteRepository extends JpaRepository<GlobalNote, Long> {
}