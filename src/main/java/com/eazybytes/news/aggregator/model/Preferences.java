package com.eazybytes.news.aggregator.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many preferences belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String keyword;      // Example: "AI"
    private String category;     // Optional: "Technology", "Health"
}
