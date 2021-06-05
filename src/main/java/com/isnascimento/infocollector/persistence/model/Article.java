package com.isnascimento.infocollector.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.isnascimento.infocollector.constants.GlobalConstants;
import com.isnascimento.infocollector.constants.PersistenceConstants;
import com.isnascimento.infocollector.constants.ValidationConstants;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@SequenceGenerator(name = PersistenceConstants.DEFAUTL_SEQUENCE, sequenceName = PersistenceConstants.NEWS_SEQUENCE, allocationSize = 1)
public class Article extends AbstractEntity {

    @NotEmpty(message = ValidationConstants.URL_CANNOT_BE_EMPTY)
    private String url;
    private String title;
    private String subtitle;
    private String author;
    @JsonFormat(pattern = GlobalConstants.DATE_TIME_FORMAT)
    private LocalDateTime dateTime;
    @Lob
    private String content;

    public Article() {
    }

    public Article(@NotEmpty(message = ValidationConstants.URL_CANNOT_BE_EMPTY) String url) {
        this.url = url;
    }

    public Article(long id, @NotEmpty(message = ValidationConstants.URL_CANNOT_BE_EMPTY)
            String url, String title, String author, LocalDateTime dateTime) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.author = author;
        this.dateTime = dateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
