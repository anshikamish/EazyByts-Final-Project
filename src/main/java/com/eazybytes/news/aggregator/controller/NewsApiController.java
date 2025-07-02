package com.eazybytes.news.aggregator.controller;

import com.eazybytes.news.aggregator.service.NewsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/newsapi")
public class NewsApiController {

    @Autowired
    private NewsApiService newsApiService;

    // ✅ GET for manual fetch from browser
    @GetMapping("/fetch")
    public ResponseEntity<String> fetchNewsGET() {
        newsApiService.fetchAndSaveNews();
        return ResponseEntity.ok("✅ News fetched via GET and stored in DB.");
    }

    // ✅ POST for fetch via frontend or backend
    @PostMapping("/fetch")
    public ResponseEntity<String> fetchNewsPOST() {
        newsApiService.fetchAndSaveNews();
        return ResponseEntity.ok("📰 News fetched via POST and saved successfully!");
    }
    
   

}
