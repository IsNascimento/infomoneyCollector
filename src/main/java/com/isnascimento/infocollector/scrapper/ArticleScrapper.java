package com.isnascimento.infocollector.scrapper;

import com.isnascimento.infocollector.constants.ErrorConstants;
import com.isnascimento.infocollector.error.exception.CouldNotConnectToGivenUrlException;
import com.isnascimento.infocollector.persistence.model.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Arrays;

import static com.isnascimento.infocollector.constants.LogConstants.*;
import static com.isnascimento.infocollector.constants.ScrapperConstants.*;

@Service
public class ArticleScrapper {

    private static final String[] contentCollectibleTags = {HTML_TAG_H1, HTML_TAG_H2, HTML_TAG_H3, HTML_TAG_H4, HTML_TAG_H5, HTML_TAG_H6, HTML_TAG_P};
    private static final Logger LOG = LoggerFactory.getLogger(ArticleScrapper.class);

    public Article createArticleObject(final String url) {
        final Document document = tryGetDocument(url);
        if (document == null) {
            return null;
        }

        LOG.info(COLLECTING_DATA);

        final Article article = new Article(url);
        article.setTitle(getArticleTitleFromDocument(document));
        article.setSubtitle(getArticleSubtitleFromDocument(document));
        article.setAuthor(getArticleAuthorFromDocument(document));
        article.setDateTime(getArticlePublicationDateTimeFromDocument(document));
        article.setContent(getArticleContentFromDocument(document));

        LOG.info(FINISHED_DATA_COLLECT);

        return article;
    }

    private Document tryGetDocument(final String url) {
        LOG.info(CONNECTING_TO_URL);

        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw new CouldNotConnectToGivenUrlException(ErrorConstants.COULD_NOT_CONNECT_TO_WEB_SITE);
        }
    }

    private String getArticleTitleFromDocument(final Document document) {
        final Element titleElement = document.getElementsByClass(ARTICLE_TITLE_CLASS).first();
        return getElementTextOrEmptyString(titleElement);
    }

    private String getArticleSubtitleFromDocument(final Document document) {
        final Element subtitleElement = document.getElementsByClass(ARTICLE_SUBTITLE_CLASS).first();
        return getElementTextOrEmptyString(subtitleElement);
    }

    private String getArticleAuthorFromDocument(final Document document) {
        final Element authorElement = document.getElementsByClass(ARTICLE_AUTHOR_NAME_CLASS).first().getElementsByTag(HTML_TAG_A).first();
        return getElementTextOrEmptyString(authorElement);
    }

    private LocalDateTime getArticlePublicationDateTimeFromDocument(final Document document) {
        final Element dateTimeElement = document.getElementsByClass(ARTICLE_POSTED_DATE_CLASS).first().getElementsByTag(HTML_TAG_TIME).last();
        final String dateTimeString = dateTimeElement.attr(TAG_ATTRIBUTE_DATE_TIME);
        return dateTimeString.isEmpty() ? null : OffsetDateTime.parse(dateTimeString).toLocalDateTime();
    }

    private String getArticleContentFromDocument(final Document document) {
        final Element contentElement = document.getElementsByClass(ARTICLE_CONTENT_CLASS).first();

        final StringBuilder contentBuilder = new StringBuilder();
        for (Element element : contentElement.getAllElements()) {
            if (Arrays.stream(contentCollectibleTags).noneMatch(element.tagName()::equals)) {
                continue;
            }
            if (!element.tagName().equals(HTML_TAG_P)) {
                contentBuilder.append(NEW_LINE);
            }
            contentBuilder.append(element.text());
            contentBuilder.append(NEW_LINE);
        }

        return contentBuilder.toString();
    }

    private String getElementTextOrEmptyString(final Element element) {
        return element != null ? element.text() : "";
    }
}
