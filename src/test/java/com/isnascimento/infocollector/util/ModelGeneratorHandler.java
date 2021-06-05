package com.isnascimento.infocollector.util;

import com.isnascimento.infocollector.persistence.model.Article;

public class ModelGeneratorHandler {

    public static Article setArticleId(final Article article) {
        article.setId(1L);
        return article;
    }
}
