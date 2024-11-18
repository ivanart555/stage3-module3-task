package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.ServiceException;
import com.mjc.school.service.validator.ValidatorInstance;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsServiceImplTest {

    private BaseRepository<NewsModel, Long> newsRepository;
    private BaseRepository<AuthorModel, Long> authorRepository;
    private NewsServiceImpl newsService;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        newsRepository = mock(BaseRepository.class);
        authorRepository = mock(BaseRepository.class);

        ValidatorInstance validatorInstanceMock = mock(ValidatorInstance.class);
        Validator validatorMock = mock(Validator.class);
        when(validatorInstanceMock.getVALIDATOR()).thenReturn(validatorMock);
        when(validatorMock.validate(any())).thenReturn(Set.of());

        newsService = new NewsServiceImpl(newsRepository, validatorInstanceMock);
    }

    @Test
    void whenReadById_thenReturnNewsDto() {
        Long newsId = 1L;
        NewsModel news = new NewsModel();
        news.setId(newsId);
        when(newsRepository.readById(newsId)).thenReturn(Optional.of(news));

        NewsDtoResponse result = newsService.readById(newsId);

        assertEquals(newsId, result.id());
        verify(newsRepository).readById(newsId);
    }

    @Test
    void whenReadById_thenThrowServiceException() {
        Long newsId = 1L;
        when(newsRepository.readById(newsId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> newsService.readById(newsId));
        assertTrue(exception.getMessage().contains("News with id " + newsId + " does not exist"));
    }

    @Test
    void whenCreateNews_thenReturnCreatedNewsDto() {
        NewsDtoRequest createRequest = new NewsDtoRequest(1L, "Title", "Content", 1L, null);
        NewsModel newsModel = new NewsModel();
        newsModel.setId(1L);
        newsModel.setTitle("Title");

        when(newsRepository.create(any(NewsModel.class))).thenReturn(newsModel);

        NewsDtoResponse response = newsService.create(createRequest);

        assertEquals(1L, response.id());
        verify(newsRepository).create(any(NewsModel.class));
    }

    @Test
    void whenUpdateNews_thenReturnUpdatedNewsDto() {
        NewsDtoRequest updateRequest = new NewsDtoRequest(1L, "Updated Title", "Updated Content", 1L, null);
        NewsModel newsModel = new NewsModel();
        newsModel.setId(1L);
        newsModel.setTitle("Updated Title");

        when(newsRepository.readById(1L)).thenReturn(Optional.of(newsModel));
        when(authorRepository.readById(1L)).thenReturn(Optional.of(new AuthorModel()));
        when(newsRepository.update(any(NewsModel.class))).thenReturn(newsModel);

        NewsDtoResponse response = newsService.update(updateRequest);

        assertEquals(1L, response.id());
        assertEquals("Updated Title", response.title());
        verify(newsRepository).update(any(NewsModel.class));
    }

    @Test
    void whenDeleteById_thenReturnTrue() {
        Long newsId = 1L;
        when(newsRepository.existById(newsId)).thenReturn(true);
        when(newsRepository.deleteById(newsId)).thenReturn(true);

        boolean result = newsService.deleteById(newsId);

        assertTrue(result);
        verify(newsRepository).deleteById(newsId);
    }
}
