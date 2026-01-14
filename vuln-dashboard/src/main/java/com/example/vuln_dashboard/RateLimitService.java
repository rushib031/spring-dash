package com.example.vuln_dashboard;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class RateLimitService {
    private final Bucket bucket;

    public RateLimitService() {
        // Updated to use the Builder pattern instead of deprecated static methods
        Bandwidth limit = Bandwidth.builder()
                .capacity(10)
                .refillIntervally(10, Duration.ofMinutes(1))
                .build();

        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public boolean tryConsume() {
        return bucket.tryConsume(1);
    }
}