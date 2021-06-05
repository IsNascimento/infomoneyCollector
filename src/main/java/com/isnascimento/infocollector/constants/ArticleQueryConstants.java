package com.isnascimento.infocollector.constants;

public class ArticleQueryConstants {

    public static final String STRING_PARAM = "string";

    public static final String LIST_MAIN_ATTRIBUTES = "SELECT new Article(a.id, a.url, a.title, a.author, a.dateTime) FROM Article a ORDER BY a.dateTime DESC";
    public static final String FIND_BY_TITLE = "SELECT new Article(a.id, a.url, a.title, a.author, a.dateTime) FROM Article a WHERE LOWER(a.title) like LOWER(concat('%', concat(?1, '%'))) ORDER BY a.dateTime DESC";
}
