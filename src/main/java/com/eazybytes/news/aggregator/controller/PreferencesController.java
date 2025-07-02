package com.eazybytes.news.aggregator.controller;

import com.eazybytes.news.aggregator.model.Preferences;
import com.eazybytes.news.aggregator.model.User;
import com.eazybytes.news.aggregator.service.PreferencesService;
import com.eazybytes.news.aggregator.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/preferences")
@CrossOrigin(origins = "*")
public class PreferencesController {

    @Autowired
    private PreferencesService preferencesService;

    @Autowired
    private UserService userService;

    // ✅ Save user preference safely
    @PostMapping("/save")
    public Preferences savePreference(@RequestBody Preferences preference) {
        String email = preference.getUser().getEmail(); // ✅ Yeh line important hai
        Optional<User> userOpt = userService.getUserByEmail(email);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }
        preference.setUser(userOpt.get());
        return preferencesService.savePreference(preference);
    }

    // ✅ Get preferences of user by email
    @GetMapping("/{email}")
    public List<Preferences> getUserPreferences(@PathVariable String email) {
        Optional<User> userOpt = userService.getUserByEmail(email);
        return userOpt.map(preferencesService::getPreferencesByUser).orElse(null);
    }

    // ✅ DTO for request body
    @Data
    public static class PreferenceRequestDto {
        private String email;
        private String keyword;
        private String category;
    }
}
