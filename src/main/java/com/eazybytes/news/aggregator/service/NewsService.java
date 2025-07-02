package com.eazybytes.news.aggregator.service;

import com.eazybytes.news.aggregator.model.NewsArticle;
import com.eazybytes.news.aggregator.repository.NewsArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsArticleRepository newsRepo;

    public List<NewsArticle> getAllArticles() {
        return newsRepo.findAll();
    }

    public List<NewsArticle> searchArticles(String keyword) {
        return newsRepo.searchByKeyword(keyword);  // ✅ Safe custom query
    }

    public List<NewsArticle> filterByCategory(String category) {
        return newsRepo.findByCategoryIgnoreCase(category);
    }

    public NewsArticle saveArticle(NewsArticle article) {
        return newsRepo.save(article);
    }

    public List<NewsArticle> getLatestNews() {
        return newsRepo.findTop10ByOrderByPublishedAtDesc();
    }
}
