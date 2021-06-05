package com.isnascimento.infocollector.persistence.repository;

import com.isnascimento.infocollector.persistence.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.isnascimento.infocollector.constants.ArticleQueryConstants.*;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

    @Query(value = LIST_MAIN_ATTRIBUTES)
    List<Article> findAll();

    @Query(value = FIND_BY_TITLE)
    List<Article> findByTitleContainingIgnoreCase(@Param(STRING_PARAM) final String string);
}
