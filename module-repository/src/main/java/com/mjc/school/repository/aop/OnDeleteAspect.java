package com.mjc.school.repository.aop;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OnDeleteAspect {
    private final BaseRepository<NewsModel, Long> newsRepository;

    public OnDeleteAspect(BaseRepository<NewsModel, Long> newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Pointcut("@annotation(OnDelete) && args(authorId)")
    public void deleteByIdCall(Long authorId) {
    }

    @After(value = "deleteByIdCall(authorId)", argNames = "authorId")
    public void afterDeleteAuthor(Long authorId) {
        newsRepository.readAll().removeIf(x -> x.getAuthor().getId().equals(authorId));
    }


}
