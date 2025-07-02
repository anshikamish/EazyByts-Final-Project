package com.eazybytes.news.aggregator.controller;

import com.eazybytes.news.aggregator.model.NewsArticle;
import com.eazybytes.news.aggregator.repository.NewsArticleRepository;
import com.eazybytes.news.aggregator.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsArticleRepository newsRepo;


    @GetMapping("/all")
    public List<NewsArticle> getAllNews() {
        return newsService.getAllArticles();
    }

    @GetMapping("/search")
    public List<NewsArticle> searchNews(@RequestParam String keyword) {
        return newsService.searchArticles(keyword);
    }

    @GetMapping("/category")
    public List<NewsArticle> filterByCategory(@RequestParam String category) {
        return newsService.filterByCategory(category);
    }

    @PostMapping("/save")
    public NewsArticle saveNews(@RequestBody NewsArticle article) {
        return newsService.saveArticle(article);
    }

    // ✅ NEW: Get latest 10 news by date
    @GetMapping("/latest")
    public List<NewsArticle> getLatestNews() {
        return newsService.getLatestNews();  // ye method tum service me add kro
    }
    
    @GetMapping("/country")
    public List<NewsArticle> getNewsByCountry(@RequestParam String country) {
        return newsRepo.findTop10ByCountryOrderByPublishedAtDesc(country);
    }

}
