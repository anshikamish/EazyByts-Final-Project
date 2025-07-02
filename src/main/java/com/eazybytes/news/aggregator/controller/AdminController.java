package com.eazybytes.news.aggregator.controller;

import com.eazybytes.news.aggregator.model.User;
import com.eazybytes.news.aggregator.repository.NewsArticleRepository;
import com.eazybytes.news.aggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private NewsArticleRepository newsRepo;

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> map = new HashMap<>();
        List<User> users = userRepo.findAll();

        map.put("totalUsers", users.size());
        map.put("users", users);
        map.put("totalArticles", newsRepo.findAll().size());

        // Sample Category Count
        Map<String, Integer> catCount = new HashMap<>();
        newsRepo.findAll().forEach(n -> {
            String cat = n.getCategory() != null ? n.getCategory() : "Uncategorized";
            catCount.put(cat, catCount.getOrDefault(cat, 0) + 1);
        });
        map.put("topCategories", catCount);

        return map;
    }
}
