package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.datasource.Datasource;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class NewsRepositoryImpl implements BaseRepository<NewsModel, Long> {
    private final Datasource datasource;
    @PersistenceContext
    private EntityManager em;

    public NewsRepositoryImpl(Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public List<NewsModel> readAll() {
        return datasource.getNews();
    }

    @Override
    public Optional<NewsModel> readById(Long newsId) {
        return datasource.getNews().stream()
                .filter(news -> newsId.equals(news.getId()))
                .findFirst();
    }

    @Override
    public NewsModel create(NewsModel entity) {
//        entity.setId(getNextId());
//        datasource.getNews().add(entity);
//        return entity;
        em.persist(entity);
        return entity;
    }

    @Override
    public NewsModel update(NewsModel entity) {
        NewsModel news = readById(entity.getId()).orElseThrow();

        news.setTitle(entity.getTitle());
        news.setContent(entity.getContent());
        news.setAuthor(entity.getAuthor());
        news.setLastUpdateDate(entity.getLastUpdateDate());

        return news;
    }

    @Override
    public boolean deleteById(Long newsId) {
        List<NewsModel> deleteList = new ArrayList<>();
        deleteList.add(this.readById(newsId).orElseThrow());
        return datasource.getNews().removeAll(deleteList);
    }

    @Override
    public boolean existById(Long newsId) {
        return datasource.getNews().stream().anyMatch(news -> newsId.equals(news.getId()));
    }

    private synchronized Long getNextId() {
        long maxId = datasource.getNews().stream()
                .mapToLong(NewsModel::getId)
                .max()
                .orElse(0L);

        return maxId + 1;
    }
}
