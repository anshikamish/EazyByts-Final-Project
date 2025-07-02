package com.eazybytes.news.aggregator.repository;

import com.eazybytes.news.aggregator.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {

    List<NewsArticle> findByCategoryIgnoreCase(String category);

    @Query("SELECT n FROM NewsArticle n WHERE LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<NewsArticle> searchByKeyword(@Param("keyword") String keyword);


    List<NewsArticle> findTop10ByOrderByPublishedAtDesc();

    boolean existsByUrl(String url);

    List<NewsArticle> findTop10ByCountryOrderByPublishedAtDesc(String country);

    List<NewsArticle> findByCountryIgnoreCase(String country);
}
