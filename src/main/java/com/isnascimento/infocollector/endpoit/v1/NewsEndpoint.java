package com.isnascimento.infocollector.endpoit.v1;

import com.isnascimento.infocollector.persistence.model.Article;
import com.isnascimento.infocollector.persistence.repository.ArticleRepository;
import com.isnascimento.infocollector.service.ArticleService;
import com.isnascimento.infocollector.util.EndpointUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.isnascimento.infocollector.constants.EndpointConstants.*;

@RestController
@RequestMapping(NEWS_CONTEXT)
public class NewsEndpoint {

    private final EndpointUtil endpointUtil;
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    public NewsEndpoint(EndpointUtil endpointUtil, ArticleService articleService, ArticleRepository articleRepository) {
        this.endpointUtil = endpointUtil;
        this.articleService = articleService;
        this.articleRepository = articleRepository;
    }

    @ApiOperation(value = "Returns a list with all news", response = Article.class)
    @GetMapping(path = LIST)
    public ResponseEntity<?> listNews() {
        return new ResponseEntity<>(articleRepository.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Returns a new by its id", response = Article.class)
    @GetMapping(path = PARAM_ID)
    public ResponseEntity<?> findNewsById(@PathVariable final long id) {
        return endpointUtil.returnObjectOrNotFound(articleRepository.findById(id));
    }

    @ApiOperation(value = "Returns a list with news filtered by title", response = Article.class)
    @GetMapping(path = TITLE + PARAM_STRING)
    public ResponseEntity<?> findNewsByTitle(@PathVariable final String string) {
        return endpointUtil.returnListOrNotFound(articleRepository.findByTitleContainingIgnoreCase(string));
    }

    @ApiOperation(value = "Return the collected news of the given URL", response = Article.class)
    @PostMapping
    public ResponseEntity<?> createNews(@RequestBody final String url) {
        articleService.validateInfoMoneyUrl(url);
        return new ResponseEntity<>(articleService.collectAndSaveArticle(url), HttpStatus.CREATED);
    }

}
