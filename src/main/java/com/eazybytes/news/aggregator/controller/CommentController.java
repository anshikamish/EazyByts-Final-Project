package com.eazybytes.news.aggregator.controller;

import com.eazybytes.news.aggregator.model.Comment;
import com.eazybytes.news.aggregator.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/save")
    public Comment save(@RequestBody Comment comment) {
        return commentService.saveComment(comment);
    }

    @GetMapping("/article/{articleId}")
    public List<Comment> getComments(@PathVariable Long articleId) {
        return commentService.getCommentsForArticle(articleId);
    }
}
