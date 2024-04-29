package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.aop.OnDelete;
import com.mjc.school.repository.datasource.Datasource;
import com.mjc.school.repository.model.AuthorModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements BaseRepository<AuthorModel, Long> {
    private final Datasource datasource;

    public AuthorRepositoryImpl(Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public List<AuthorModel> readAll() {
        return datasource.getAuthors();
    }

    @Override
    public Optional<AuthorModel> readById(Long authorId) {
        return datasource.getAuthors().stream().filter(author -> authorId.equals(author.getId())).findFirst();
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        entity.setId(getNextId());
        datasource.getAuthors().add(entity);
        return entity;
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        AuthorModel author = readById(entity.getId()).orElseThrow();

        author.setName(entity.getName());
        author.setLastUpdateDate(entity.getLastUpdateDate());

        return author;
    }

    @Override
    @OnDelete
    public boolean deleteById(Long authorId) {
        List<AuthorModel> deleteList = new ArrayList<>();
        deleteList.add(this.readById(authorId).orElseThrow());
        return datasource.getAuthors().removeAll(deleteList);
    }

    @Override
    public boolean existById(Long authorId) {
        return datasource.getAuthors().stream().anyMatch(author -> authorId.equals(author.getId()));
    }

    private synchronized Long getNextId() {
        long maxId = datasource.getAuthors().stream()
                .mapToLong(AuthorModel::getId)
                .max()
                .orElse(0L);

        return maxId + 1;
    }
}
