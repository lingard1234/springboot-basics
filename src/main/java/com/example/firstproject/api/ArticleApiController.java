package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.firstproject.entity.Article;

import java.util.ArrayList;
import java.util.List;
import com.example.firstproject.service.ArticleService;


@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService; // 서비스 객체 주입

    //GET
    @GetMapping("/api/articles")
    public List<Article> getArticles() {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    // POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.CREATED).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(deleted) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos) {
        List<Article> createdList = articleService.createArticles(dtos);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}




//   @Autowired
//    private ArticleRepository articleRepository;

    // GET
//    @GetMapping("/api/articles")
//    public List<Article> index() {
//      return articleRepository.findAll();
//    }
//
//    @GetMapping("/api/articles/{id}")
//    public Article show(@PathVariable long id) {
//        return articleRepository.findById(id).orElse(null);
//    }



    // PATCH
//    @PatchMapping("/api/articles/{id}")
//    public ResponseEntity<Article> update(@PathVariable long id, @RequestBody ArticleForm dto) {
//        Article article = dto.toEntity();
//        log.info("id: {}, article: {}", id, article.toString());
//        Article target = articleRepository.findById(id).orElse(null);
//        if (target == null || id != article.getId()) {
//            log.info("잘못된 요청! id: {}, article: {}", id, article);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        target.patch(article);
//        Article updated = articleRepository.save(target);
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
//    }

    // DELETE
//    @DeleteMapping("/api/articles/{id}")
//    public ResponseEntity<Void> delete(@PathVariable long id) {
//        Article target = articleRepository.findById(id).orElse(null);
//        if (target == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        articleRepository.delete(target);
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }

