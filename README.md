🛡️ VulnScan: Security Management Dashboard
VulnScan is a full-stack Java Spring Boot application designed to centralize vulnerability tracking with a focus on Defense-in-Depth security principles. This project simulates a Security Operations Center (SOC) dashboard where analysts can monitor and manage CVE entries in real-time.

🌐 [https://spring-dash-production.up.railway.app/dashboard]


🚀 Technical Highlights
This project goes beyond basic CRUD operations by implementing three core security pillars:

Role-Based Access Control (RBAC): Leverages Spring Security to differentiate between "Analysts" (Read-only) and "Admins" (Full CRUD + Global Briefings).

API Rate Limiting: Integrated Bucket4j with a token-bucket algorithm to protect the backend from resource exhaustion and automated spamming.

Security Validation: Implemented JSR-303 Bean Validation with custom Regex patterns to ensure only industry-standard CVE IDs (e.g., CVE-YYYY-NNNN) enter the system.

📸 Dashboard Preview
**
<img width="2560" height="836" alt="image" src="https://github.com/user-attachments/assets/d3671f40-6d97-4208-a19a-2b08edd089ff" />

**

🛠️ Tech Stack
Backend: Java 21/25, Spring Boot 3.x, Spring Security.

Frontend: Thymeleaf (Spring Security Dialect), JavaScript (Fetch API), CSS3.

Database: H2 In-Memory Database.

Security: BCrypt Password Hashing, CSRF Protection, and Rate Limiting via Bucket4j.

Infrastructure: Maven for dependency management and Railway for cloud deployment.

🔐 Security Features Deep Dive
1. Authentication & Authorization
Uses a custom SecurityFilterChain to enforce session-based authentication. Passwords are never stored in plain text; they are hashed using BCrypt with a unique salt for every user.

Admin: Access to /api/vulnerabilities (POST/DELETE) and /api/notes.

User/Analyst: Access to /dashboard and search functionality only.

2. Input Integrity
All data entering the Vulnerability entity is validated at the persistence layer.

Java

@Pattern(regexp = "^CVE-\\d{4}-\\d{3,7}$", message = "Invalid CVE format")
private String cveId;
3. Availability Protection
The RateLimitService restricts the creation of new vulnerabilities to 10 requests per minute per session, mitigating potential Denial-of-Service (DoS) attempts on the database.

📂 Project Structure
Plaintext

vuln-dashboard/
├── src/main/java/com/example/vuln_dashboard/
│   ├── SecurityConfig.java   # RBAC & Password Encoding
│   ├── RateLimitService.java # Bucket4j Implementation
│   ├── DataLoader.java       # Mock Security Data
│   └── Vulnerability.java    # Entity with Validation
├── src/main/resources/
│   ├── templates/            # Thymeleaf HTML Views
│   └── static/               # CSS and Dashboard JS
└── pom.xml                   # Maven Dependencies
⚡ Quick Start (Local)
Clone the repository.

Set an environment variable: ADMIN_PASSWORD=yourpassword.

Run the application using Maven:

Bash

./mvnw spring-boot:run
Access the dashboard at http://localhost:8080/dashboard.
