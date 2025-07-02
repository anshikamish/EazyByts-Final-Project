package com.eazybytes.news.aggregator.service;

import com.eazybytes.news.aggregator.model.Preferences;
import com.eazybytes.news.aggregator.model.User;

import java.util.List;

public interface PreferencesService {
    Preferences savePreference(Preferences preference);
    List<Preferences> getPreferencesByUser(User user);
}
