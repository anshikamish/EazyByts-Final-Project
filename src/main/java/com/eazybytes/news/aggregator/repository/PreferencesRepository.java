package com.eazybytes.news.aggregator.repository;

import com.eazybytes.news.aggregator.model.Preferences;
import com.eazybytes.news.aggregator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
    List<Preferences> findByUser(User user);
}
