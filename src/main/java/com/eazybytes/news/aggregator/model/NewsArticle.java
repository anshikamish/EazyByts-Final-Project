package com.eazybytes.news.aggregator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news_articles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    private String source;
    private String author;
    private String url;
    
    private String category;
    
    @Column
    private String country;



    @Column(name = "published_at")
    private LocalDateTime publishedAt;

}
