package com.example.demo.article;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> getList() {
        return this.articleRepository.findAll();
    }

    public void create(String subject, String content) {
        Article article = new Article();
        article.setSubject(subject);
        article.setContent(content);
        article.setCreateDate(LocalDateTime.now());
        this.articleRepository.save(article);
    }

    public Article findById(Integer id) {
        Optional<Article> article = this.articleRepository.findById(id);
        if (article.isEmpty()) {
            throw new RuntimeException("존재하지 않는 게시글");
        }
        return article.get();
    }

    public void modify(Article article, String subject, String content) {
        article.setSubject(subject);
        article.setContent(content);
        this.articleRepository.save(article);
    }

    public void remove(Integer id) {
        Article article = this.findById(id);
        this.articleRepository.delete(article);
    }

}
