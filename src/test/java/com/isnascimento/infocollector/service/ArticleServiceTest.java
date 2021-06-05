package com.isnascimento.infocollector.service;

import com.isnascimento.infocollector.constants.ValidationConstants;
import com.isnascimento.infocollector.error.exception.InvalidUrlException;
import com.isnascimento.infocollector.persistence.model.Article;
import com.isnascimento.infocollector.persistence.repository.ArticleRepository;
import com.isnascimento.infocollector.scrapper.ArticleScrapper;
import com.isnascimento.infocollector.util.ModelGenerator;
import com.isnascimento.infocollector.util.ModelGeneratorHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for ArticleService class")
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;
    @Mock
    private ArticleScrapper articleScrapperMock;
    @Mock
    private ArticleRepository articleRepositoryMock;
    private final ModelGenerator modelGenerator = new ModelGenerator();

    @BeforeEach
    void setUp() {
        BDDMockito.when(articleRepositoryMock.save(ArgumentMatchers.any(Article.class)))
                .thenAnswer(i -> ModelGeneratorHandler.setArticleId((Article) i.getArguments()[0]));

        BDDMockito.when(articleScrapperMock.createArticleObject(ArgumentMatchers.anyString()))
                .thenReturn(modelGenerator.expectedInfoMoneyArticle());
    }

    @Test
    @DisplayName("collectAndSaveArticle should collect data from the given url, create article object and persist it when successful")
    void collectAndSaveArticleShouldCollectAndPersistArticleFromGivenUrl() {
        final Article expectedArticle = modelGenerator.expectedInfoMoneyArticle();
        final Article savedArticle = articleService.collectAndSaveArticle(expectedArticle.getUrl());

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle.getId()).isNotNull();
        assertThat(savedArticle.getUrl()).isEqualTo(expectedArticle.getUrl());
        assertThat(savedArticle.getTitle()).isEqualTo(expectedArticle.getTitle());
        assertThat(savedArticle.getSubtitle()).isEqualTo(expectedArticle.getSubtitle());
        assertThat(savedArticle.getAuthor()).isEqualTo(expectedArticle.getAuthor());
        assertThat(savedArticle.getDateTime()).isEqualTo(expectedArticle.getDateTime());
        assertThat(savedArticle.getContent()).isEqualTo(expectedArticle.getContent());
    }

    @Test
    @DisplayName("validateInfoMoneyUrl should do nothing when url is valid and from infoMoney web site")
    void validateInfoMoneyUrlShouldDoNothingWhenUrlIsValidAndSupported() {
        assertThatCode(() -> articleService.validateInfoMoneyUrl("https://www.infomoney.com.br/financas/monte"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("validateInfoMoneyUrl should throw InvalidUrlException when url is null")
    void validateInfoMoneyUrlThrowsInvalidUrlExceptionWhenUrlIsNull() {
        assertThatExceptionOfType(InvalidUrlException.class)
                .isThrownBy(() -> articleService.validateInfoMoneyUrl(null))
                .withMessageContaining(ValidationConstants.INVALID_URL);
    }

    @Test
    @DisplayName("validateInfoMoneyUrl should throw InvalidUrlException when url is invalid")
    void validateInfoMoneyUrlThrowsInvalidUrlExceptionWhenUrlIsInvalid() {
        assertThatExceptionOfType(InvalidUrlException.class)
                .isThrownBy(() -> articleService.validateInfoMoneyUrl("invalid_url"))
                .withMessageContaining(ValidationConstants.INVALID_URL);
    }

    @Test
    @DisplayName("validateInfoMoneyUrl should throw InvalidUrlException when url is not from infoMoney web site")
    void validateInfoMoneyUrlThrowsInvalidUrlExceptionWhenUrlIsNotFromInfoMoney() {
        assertThatExceptionOfType(InvalidUrlException.class)
                .isThrownBy(() -> articleService.validateInfoMoneyUrl("https://www.google.com/"))
                .withMessageContaining(ValidationConstants.WEB_SITE_NOT_SUPPORTED);
    }

}