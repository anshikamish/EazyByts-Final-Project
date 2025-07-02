package com.eazybytes.news.aggregator.service;

import com.eazybytes.news.aggregator.model.Preferences;
import com.eazybytes.news.aggregator.model.User;
import com.eazybytes.news.aggregator.repository.PreferencesRepository;
//import com.eazybytes.news.aggregator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreferencesServiceImpl implements PreferencesService {

    @Autowired
    private PreferencesRepository preferencesRepository;

    @Autowired
    private UserService userService;

    @Override
    public Preferences savePreference(Preferences preference) {
        // Step 1: validate
        if (preference.getUser() == null || preference.getUser().getEmail() == null) {
            throw new RuntimeException("❌ User email is required to save preferences.");
        }

        // Step 2: get full user from DB by email
        Optional<User> userOptional = userService.getUserByEmail(preference.getUser().getEmail());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("❌ No user found with email: " + preference.getUser().getEmail());
        }

        // Step 3: set user entity properly
        preference.setUser(userOptional.get());

        // Step 4: save preference
        return preferencesRepository.save(preference);
    }

    @Override
    public List<Preferences> getPreferencesByUser(User user) {
        return preferencesRepository.findByUser(user);
    }
}
