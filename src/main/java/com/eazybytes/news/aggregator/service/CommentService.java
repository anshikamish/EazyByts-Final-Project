package com.eazybytes.news.aggregator.service;

import com.eazybytes.news.aggregator.model.Comment;
import com.eazybytes.news.aggregator.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;

    public Comment saveComment(Comment comment) {
        return commentRepo.save(comment);
    }

    public List<Comment> getCommentsForArticle(Long articleId) {
        return commentRepo.findByArticleId(articleId);
    }
}
