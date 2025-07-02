package com.eazybytes.news.aggregator.service;

import com.eazybytes.news.aggregator.model.NewsArticle;
import com.eazybytes.news.aggregator.repository.NewsArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class NewsApiService {

    @Autowired
    private NewsArticleRepository newsRepo;

    private final String API_KEY = "23f236efda0a4477aaa0b4793007ab5e";

    // 🔄 Auto-fetch every 6 hours (cron: every 6th hour)
    @Scheduled(cron = "0 0 */6 * * *")
    public void autoFetchNewsFromApi() {
        System.out.println("🔁 Auto-fetching news...");
        fetchAndSaveNews();
    }

    public void fetchAndSaveNews() {
        List<String> countries = Arrays.asList("in", "us", "gb", "au", "de", "fr");

        for (String country : countries) {
            String url = "https://newsapi.org/v2/top-headlines?country=" + country + "&pageSize=100&apiKey=" + API_KEY;
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || response.get("articles") == null) continue;

            List<Map<String, Object>> articles = (List<Map<String, Object>>) response.get("articles");
            List<NewsArticle> newsList = new ArrayList<>();

            for (Map<String, Object> article : articles) {
                Map<String, Object> sourceMap = (Map<String, Object>) article.get("source");

                String title = (String) article.get("title");
                String urlLink = (String) article.get("url");
                String publishedAtStr = (String) article.get("publishedAt");

                if (urlLink == null || newsRepo.existsByUrl(urlLink)) continue;

                LocalDateTime publishedAt;
                try {
                    publishedAt = publishedAtStr != null
                            ? OffsetDateTime.parse(publishedAtStr).toLocalDateTime()
                            : LocalDateTime.now();
                } catch (DateTimeParseException e) {
                    publishedAt = LocalDateTime.now();
                }

                NewsArticle news = NewsArticle.builder()
                        .title(title != null ? title : "Untitled")
                        .content((String) article.get("description"))
                        .author((String) article.get("author"))
                        .url(urlLink)
                        .source(sourceMap != null ? (String) sourceMap.get("name") : "Unknown")
                        .publishedAt(publishedAt)
                        .category("General")
                        .country(country)
                        .build();

                newsList.add(news);
            }

            System.out.println("📥 Saving " + newsList.size() + " articles for " + country);
            newsRepo.saveAll(newsList);
        }
    }

    }

       
   
