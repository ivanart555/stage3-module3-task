package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.aop.OnDelete;
import com.mjc.school.repository.model.AuthorModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements BaseRepository<AuthorModel, Long> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<AuthorModel> readAll() {
        TypedQuery<AuthorModel> query = em.createQuery("select a from AuthorModel a", AuthorModel.class);
        return query.getResultList();
    }

    @Override
    public Optional<AuthorModel> readById(Long authorId) {
        return Optional.ofNullable(em.find(AuthorModel.class, authorId));
    }

    @Transactional
    @Override
    public AuthorModel create(AuthorModel entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public AuthorModel update(AuthorModel entity) {
        AuthorModel author = em.find(AuthorModel.class, entity.getId());
        author.setName(entity.getName());
        em.merge(author);
        return author;
    }

    @Transactional
    @Override
    @OnDelete
    public boolean deleteById(Long authorId) {
        if (existById(authorId)) {
            em.remove(em.find(AuthorModel.class, authorId));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long authorId) {
        return readById(authorId).isPresent();
    }
}
