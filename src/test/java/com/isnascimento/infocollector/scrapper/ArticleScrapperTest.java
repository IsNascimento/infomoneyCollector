package com.isnascimento.infocollector.scrapper;

import com.isnascimento.infocollector.constants.ErrorConstants;
import com.isnascimento.infocollector.error.exception.CouldNotConnectToGivenUrlException;
import com.isnascimento.infocollector.persistence.model.Article;
import com.isnascimento.infocollector.util.ModelGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@RunWith(SpringRunner.class)
@DisplayName("Tests for ArticleScrapper class")
class ArticleScrapperTest {

    private final ArticleScrapper articleScrapper = new ArticleScrapper();
    private final ModelGenerator modelGenerator = new ModelGenerator();

    @Test
    @DisplayName("createArticleObject should return article with data from the given url when successful")
    void createArticleObjectShouldReturnArticleWithDataFromGivenUrl() {
        final Article expectedArticle = modelGenerator.expectedInfoMoneyArticle();
        final Article returnedArticle = articleScrapper.createArticleObject(expectedArticle.getUrl());

        assertThat(returnedArticle).isNotNull();
        assertThat(returnedArticle.getUrl()).isEqualTo(expectedArticle.getUrl());
        assertThat(returnedArticle.getTitle()).isEqualTo(expectedArticle.getTitle());
        assertThat(returnedArticle.getSubtitle()).isEqualTo(expectedArticle.getSubtitle());
        assertThat(returnedArticle.getAuthor()).isEqualTo(expectedArticle.getAuthor());
        assertThat(returnedArticle.getDateTime()).isEqualTo(expectedArticle.getDateTime());
        assertThat(returnedArticle.getContent()).isEqualTo(expectedArticle.getContent());
    }

    @Test
    @DisplayName("createArticleObject should throw IllegalArgumentException when informed url is null")
    void createArticleObjectThrowsIllegalArgumentExceptionWhenUrlIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            articleScrapper.createArticleObject(null);
        });
    }

    @Test
    @DisplayName("createArticleObject should throw IllegalArgumentException when informed url is not valid")
    void createArticleObjectThrowsIllegalArgumentExceptionWhenUrlIsNotValid() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            articleScrapper.createArticleObject("invalid_url");
        });
    }

    @Test
    @DisplayName("createArticleObject should throw CouldNotConnectToGivenUrlException when scrapper can not connect do url")
    void createArticleObjectThrowsCouldNotConnectToGivenUrlExceptionWhenScrapperCanNotGetConnection() {
        assertThatExceptionOfType(CouldNotConnectToGivenUrlException.class).isThrownBy(() -> {
            articleScrapper.createArticleObject("https://www.infomoney.com.br/minhas-financas/monte-bravo-anuncia-tenista-bruno-soares-como-embaixador-da-marca3/");
        }).withMessageContaining(ErrorConstants.COULD_NOT_CONNECT_TO_WEB_SITE);
    }

}