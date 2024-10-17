package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.TagModel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository implements BaseRepository<TagModel, Long> {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<TagModel> readAll() {
        TypedQuery<TagModel> query = em.createQuery("select a from TagModel a", TagModel.class);
        return query.getResultList();
    }

    @Override
    public Optional<TagModel> readById(Long id) {
        return Optional.ofNullable(em.find(TagModel.class, id));
    }

    @Transactional
    @Override
    public TagModel create(TagModel entity) {
        em.persist(entity);
        return entity;
    }

    @Transactional
    @Override
    public TagModel update(TagModel entity) {
        TagModel tag = em.find(TagModel.class, entity.getId());
        tag.setName(entity.getName());
        em.merge(tag);
        return tag;
    }

    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            em.remove(em.find(TagModel.class, id));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }
}
