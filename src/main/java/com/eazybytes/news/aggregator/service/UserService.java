package com.eazybytes.news.aggregator.service;

import com.eazybytes.news.aggregator.model.User;
import com.eazybytes.news.aggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password));
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> getAllUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
   
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
