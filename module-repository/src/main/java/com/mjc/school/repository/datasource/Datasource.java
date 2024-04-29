package com.mjc.school.repository.datasource;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utils.DataGenerator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Datasource {
    private static class DatasourceHolder {
        private static final Datasource INSTANCE = new Datasource();
    }

    private final List<NewsModel> news;
    private final List<AuthorModel> authors;

    private Datasource() {
        this.news = DataGenerator.generateNews();
        this.authors = DataGenerator.generateAuthors();
    }

    public static Datasource getInstance() {
        return DatasourceHolder.INSTANCE;
    }

    public List<NewsModel> getNews() {
        return this.news;
    }

    public List<AuthorModel> getAuthors() {
        return this.authors;
    }
}