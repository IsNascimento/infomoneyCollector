package com.isnascimento.infocollector.endpoit.v1;

import com.isnascimento.infocollector.persistence.model.Article;
import com.isnascimento.infocollector.persistence.repository.ArticleRepository;
import com.isnascimento.infocollector.util.ModelGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static com.isnascimento.infocollector.constants.EndpointConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class NewsEndpointTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;
    private final ModelGenerator modelGenerator = new ModelGenerator();

    @Test
    @DisplayName("listNews should return a list with all articles when successful")
    void listNewsShouldReturnArticleListWhenSuccessful() {
        articleRepository.save(modelGenerator.createValidArticle());
        articleRepository.save(modelGenerator.createValidArticle2());
        List<Article> articleList = testRestTemplate.exchange(NEWS_CONTEXT + LIST,
                HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<List<Article>>() {
                }).getBody();

        assertThat(articleList).isNotNull().hasSize(2);
    }

    @Test
    @DisplayName("listNews should return an empty list when no articles are found")
    void listNewsShouldReturnEmptyListWhenNoArticlesAreFound() {
        final List<Article> articleList = testRestTemplate.exchange(NEWS_CONTEXT + LIST,
                HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<List<Article>>() {
                }).getBody();

        assertThat(articleList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("listNews should return status code 200 when successful")
    void listNewsShouldReturnStatusCode200WhenSuccessful() {
        final ResponseEntity<List<Article>> response = testRestTemplate.exchange(NEWS_CONTEXT + LIST,
                HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<List<Article>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("findNewsById should return an article by its id when successful")
    void findNewsByIdShouldReturnArticleByIdWhenSuccessful() {
        final Article savedArticle = articleRepository.save(modelGenerator.createValidArticle());
        final Article returnedArticle = testRestTemplate.exchange(NEWS_CONTEXT + "/1",
                HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<Article>() {
                }).getBody();

        assertThat(savedArticle).isNotNull();
        assertThat(savedArticle).isEqualTo(returnedArticle);
    }

    @Test
    @DisplayName("findNewsById should return status code 200 when successful")
    void findNewsByIdShouldReturnStatusCode200WhenSuccessful() {
        articleRepository.save(modelGenerator.createValidArticle());
        final ResponseEntity<Article> response = testRestTemplate.exchange(NEWS_CONTEXT + "/1",
                HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<Article>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("findNewsById should return status code 404 when the given id is not found")
    void findNewsByIdShouldReturnStatusCode404WhenIdIsNotFound() {
        final ResponseEntity<Article> response = testRestTemplate.exchange(NEWS_CONTEXT + "/1",
                HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<Article>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("findNewsByTitle should return an article list filtered by title when successful")
    void findNewsByTitleShouldReturnArticleListFilteredByTitleWhenSuccessful() {
        articleRepository.save(modelGenerator.createValidArticle());
        final Article savedArticle2 = articleRepository.save(modelGenerator.createValidArticle2());
        final Article savedArticle3 = articleRepository.save(modelGenerator.createValidArticle3());
        final List<Article> articleList = testRestTemplate.exchange(NEWS_CONTEXT + TITLE + "/empresas",
                HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<List<Article>>() {
                }).getBody();

        assertThat(articleList).isNotNull().hasSize(2);
        assertThat(articleList.get(0)).isEqualTo(savedArticle2);
        assertThat(articleList.get(1)).isEqualTo(savedArticle3);
    }

    @Test
    @DisplayName("findNewsByTitle should return status code 200 when successful")
    void findNewsByTitleShouldReturnStatusCode200WhenSuccessful() {
        articleRepository.save(modelGenerator.createValidArticle());
        articleRepository.save(modelGenerator.createValidArticle2());
        articleRepository.save(modelGenerator.createValidArticle3());
        final ResponseEntity<List<Article>> response = testRestTemplate.exchange(NEWS_CONTEXT + TITLE + "/empresas",
                HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<List<Article>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("findNewsByTitle should return status code 404 when no article is found")
    void findNewsByTitleShouldReturnStatusCode404WhenNoArticleIsFound() {
        articleRepository.save(modelGenerator.createValidArticle());
        articleRepository.save(modelGenerator.createValidArticle2());
        articleRepository.save(modelGenerator.createValidArticle3());
        final ResponseEntity<List<Article>> response = testRestTemplate.exchange(NEWS_CONTEXT + TITLE + "/Chewbacca",
                HttpMethod.GET, new HttpEntity<>(""), new ParameterizedTypeReference<List<Article>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("createNews should return created article when successful")
    void createNewsShouldReturnCreatedArticleWhenSuccessful() {
        final Article expectedArticle = modelGenerator.expectedInfoMoneyArticle();
        final Article article = testRestTemplate.exchange(NEWS_CONTEXT,
                HttpMethod.POST, new HttpEntity<>(expectedArticle.getUrl()), new ParameterizedTypeReference<Article>() {
                }).getBody();

        assertThat(article).isNotNull();
        assertThat(article.getId()).isNotNull();
        assertThat(article.getTitle()).isEqualTo(expectedArticle.getTitle());
    }

    @Test
    @DisplayName("createNews should return status code 201 when successful")
    void createNewsShouldReturnStatusCode201WhenSuccessful() {
        final ResponseEntity<Article> response = testRestTemplate.exchange(NEWS_CONTEXT,
                HttpMethod.POST, new HttpEntity<>(modelGenerator.expectedInfoMoneyArticle().getUrl()),
                new ParameterizedTypeReference<Article>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("createNews should return status code 400 when url is invalid")
    void createNewsShouldReturnStatusCode400WhenUrlIsInvalid() {
        final ResponseEntity<?> response = testRestTemplate.exchange(NEWS_CONTEXT,
                HttpMethod.POST, new HttpEntity<>("invalid_url"), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("createNews should return status code 400 when url is not from infoMoney web site")
    void createNewsShouldReturnStatusCode400WhenUrlIsNotFromInfoMoney() {
        final ResponseEntity<?> response = testRestTemplate.exchange(NEWS_CONTEXT,
                HttpMethod.POST, new HttpEntity<>("https://www.google.com/"), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("createNews should return status code 500 when scrapper can not connect to url")
    void createNewsShouldReturnStatusCode500WhenScrapperCanNotConnetToUrl() {
        final ResponseEntity<?> response = testRestTemplate.exchange(NEWS_CONTEXT,
                HttpMethod.POST, new HttpEntity<>("https://www.infomoney.com.br/abc/defg"), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}