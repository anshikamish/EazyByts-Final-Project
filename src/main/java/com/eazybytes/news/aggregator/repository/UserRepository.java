package com.eazybytes.news.aggregator.repository;

import com.eazybytes.news.aggregator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(String role); // ✅ get users by role
}
