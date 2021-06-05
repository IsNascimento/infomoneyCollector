package com.isnascimento.infocollector.service;

import com.isnascimento.infocollector.constants.ValidationConstants;
import com.isnascimento.infocollector.error.exception.InvalidUrlException;
import com.isnascimento.infocollector.persistence.model.Article;
import com.isnascimento.infocollector.persistence.repository.ArticleRepository;
import com.isnascimento.infocollector.scrapper.ArticleScrapper;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleScrapper scrapper;
    private final ArticleRepository articleRepository;
    private final UrlValidator urlValidator = new UrlValidator();

    private static final String SUPPORTED_WEB_SITE = "https://www.infomoney.com.br";

    public ArticleService(final ArticleScrapper scrapper, final ArticleRepository articleRepository) {
        this.scrapper = scrapper;
        this.articleRepository = articleRepository;
    }

    public Article collectAndSaveArticle(final String url) {
        return articleRepository.save(scrapper.createArticleObject(url));
    }

    public void validateInfoMoneyUrl(final String url) {
        if (!urlValidator.isValid(url)) {
            throw new InvalidUrlException(ValidationConstants.INVALID_URL);
        }
        if (!url.startsWith(SUPPORTED_WEB_SITE)) {
            throw new InvalidUrlException(ValidationConstants.WEB_SITE_NOT_SUPPORTED);
        }
    }
}
