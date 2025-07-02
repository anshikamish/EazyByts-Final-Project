package com.eazybytes.news.aggregator.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment_text", nullable = false)
    private String text;

    @Column(name = "commenter_name", nullable = false)
    private String commenterName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
    private NewsArticle article;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
