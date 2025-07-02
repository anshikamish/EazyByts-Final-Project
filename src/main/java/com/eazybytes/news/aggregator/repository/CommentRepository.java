package com.eazybytes.news.aggregator.repository;

import com.eazybytes.news.aggregator.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);
}
