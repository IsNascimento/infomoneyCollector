package com.isnascimento.infocollector.persistence.repository;

import com.isnascimento.infocollector.persistence.model.Article;
import com.isnascimento.infocollector.util.ModelGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DisplayName("Tests for ArticleRepository class")
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private EntityManager entityManager;
    private final ModelGenerator modelGenerator = new ModelGenerator();

    @Test
    @DisplayName("save should persist article when successful")
    void saveShouldPersistData() {
        final Article articleToBeSaved = modelGenerator.createValidArticle();
        final Article savedArticle = articleRepository.save(articleToBeSaved);

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getId()).isNotNull();
        assertThat(savedArticle.getUrl()).isEqualTo(articleToBeSaved.getUrl());
        assertThat(savedArticle.getTitle()).isEqualTo(articleToBeSaved.getTitle());
        assertThat(savedArticle.getSubtitle()).isEqualTo(articleToBeSaved.getSubtitle());
        assertThat(savedArticle.getAuthor()).isEqualTo(articleToBeSaved.getAuthor());
        assertThat(savedArticle.getDateTime()).isEqualTo(articleToBeSaved.getDateTime());
        assertThat(savedArticle.getContent()).isEqualTo(articleToBeSaved.getContent());
    }

    @Test
    @DisplayName("findAll should return a list with saved articles ordered by date and time when successful")
    void findAllShouldReturnListWithAllArticlesOrderedByDateTime() {
        final Article article1 = modelGenerator.createValidArticle();
        final Article article2 = modelGenerator.createValidArticle2();
        saveArticleAndFlush(article2);
        saveArticleAndFlush(article1);

        final List<Article> articleList = articleRepository.findAll();

        assertThat(articleList).isNotNull().isNotEmpty().hasSize(2);
        assertThat(articleList.get(0)).isEqualTo(article1);
        assertThat(articleList.get(1)).isEqualTo(article2);
    }

    @Test
    @DisplayName("findAll should return an empty list when no articles are found")
    void findAllShouldReturnEmptyListWhenNoArticlesAreFound() {
        final List<Article> articleList = articleRepository.findAll();

        assertThat(articleList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByTitleContainingIgnoreCase should return a list with all articles filtered by title when successful")
    void findByTitleContainingIgnoreCaseReturnsListWithArticlesFilteredByTitle() {
        final Article article1 = modelGenerator.createValidArticle();
        final Article article2 = modelGenerator.createValidArticle2();
        final Article article3 = modelGenerator.createValidArticle3();
        saveArticleAndFlush(article2);
        saveArticleAndFlush(article1);
        saveArticleAndFlush(article3);

        final List<Article> articleList = articleRepository.findByTitleContainingIgnoreCase("empresas");

        assertThat(articleList).isNotNull().isNotEmpty().hasSize(2);
        assertThat(articleList.get(0)).isEqualTo(article2);
        assertThat(articleList.get(1)).isEqualTo(article3);
    }

    @Test
    @DisplayName("findByTitleContainingIgnoreCase should return an empty list when no articles are found")
    void findByTitleContainingIgnoreCaseShouldReturnEmptyListWhenNoArticlesAreFound() {
        final Article article1 = modelGenerator.createValidArticle();
        final Article article2 = modelGenerator.createValidArticle2();
        final Article article3 = modelGenerator.createValidArticle3();
        saveArticleAndFlush(article2);
        saveArticleAndFlush(article1);
        saveArticleAndFlush(article3);

        final List<Article> articleList = articleRepository.findByTitleContainingIgnoreCase("zica");

        assertThat(articleList).isNotNull().isEmpty();
    }

    private void saveArticleAndFlush(Article article) {
        articleRepository.save(article);
        entityManager.flush();
    }
}