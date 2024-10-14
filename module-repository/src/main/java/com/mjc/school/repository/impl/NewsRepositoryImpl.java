package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class NewsRepositoryImpl implements BaseRepository<NewsModel, Long> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<NewsModel> readAll() {
        TypedQuery<NewsModel> query = em.createQuery("select m from NewsModel m", NewsModel.class);
        return query.getResultList();
    }

    @Override
    public Optional<NewsModel> readById(Long newsId) {
        return Optional.ofNullable(em.find(NewsModel.class, newsId));
    }

    @Transactional
    @Override
    public NewsModel create(NewsModel entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public NewsModel update(NewsModel entity) {
        NewsModel news = em.find(NewsModel.class, entity.getId());

        news.setTitle(entity.getTitle());
        news.setContent(entity.getContent());
        news.setAuthor(entity.getAuthor());
        em.merge(news);
        return news;
    }

    @Transactional
    @Override
    public boolean deleteById(Long newsId) {
        if (existById(newsId)) {
            em.remove(em.find(NewsModel.class, newsId));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long newsId) {
        return readById(newsId).isPresent();
    }
}
