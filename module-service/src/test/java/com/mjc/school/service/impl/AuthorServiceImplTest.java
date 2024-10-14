package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exception.ServiceException;
import com.mjc.school.service.validator.ValidatorInstance;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {

    private BaseRepository<AuthorModel, Long> authorRepository;
    private AuthorServiceImpl authorService;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        authorRepository = mock(BaseRepository.class);

        ValidatorInstance validatorInstanceMock = mock(ValidatorInstance.class);
        Validator validatorMock = mock(Validator.class);
        when(validatorInstanceMock.getVALIDATOR()).thenReturn(validatorMock);
        when(validatorMock.validate(any())).thenReturn(Set.of());

        authorService = new AuthorServiceImpl(authorRepository, validatorInstanceMock);
    }

    @Test
    void whenReadById_thenReturnAuthorDto() {

        Long authorId = 1L;
        AuthorModel author = new AuthorModel();
        author.setId(authorId);
        author.setName("John Doe");
        when(authorRepository.readById(authorId)).thenReturn(Optional.of(author));

        AuthorDtoResponse result = authorService.readById(authorId);

        assertEquals(authorId, result.id());
        verify(authorRepository).readById(authorId);
    }

    @Test
    void whenReadById_thenThrowServiceException() {
        Long authorId = 1L;
        when(authorRepository.readById(authorId)).thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> authorService.readById(authorId));
        assertTrue(exception.getMessage().contains("Author Id does not exist. Author Id is: " + authorId));
    }

    @Test
    void whenCreateAuthor_thenReturnCreatedAuthorDto() {
        AuthorDtoRequest createRequest = new AuthorDtoRequest(1L, "John Doe");

        AuthorModel authorModel = new AuthorModel();
        authorModel.setId(1L);
        authorModel.setName(createRequest.name());

        when(authorRepository.create(any(AuthorModel.class))).thenReturn(authorModel);

        AuthorDtoResponse response = authorService.create(createRequest);

        assertEquals(1L, response.id());
        assertEquals("John Doe", response.name());
        verify(authorRepository).create(any(AuthorModel.class));
    }

    @Test
    void whenUpdateAuthor_thenReturnUpdatedAuthorDto() {
        AuthorDtoRequest updateRequest = new AuthorDtoRequest(1L, "Alex Peterson");
        AuthorModel authorModel = new AuthorModel();
        authorModel.setId(1L);
        authorModel.setName("John Doe");

        when(authorRepository.readById(1L)).thenReturn(Optional.of(authorModel));
        when(authorRepository.update(any(AuthorModel.class))).thenReturn(authorModel);

        AuthorDtoResponse response = authorService.update(updateRequest);

        assertEquals(1L, response.id());
        assertEquals("Alex Peterson", response.name());
        verify(authorRepository).update(any(AuthorModel.class));
    }

    @Test
    void whenDeleteById_thenReturnTrue() {
        Long authorId = 1L;
        when(authorRepository.existById(authorId)).thenReturn(true);
        when(authorRepository.deleteById(authorId)).thenReturn(true);

        boolean result = authorService.deleteById(authorId);

        assertTrue(result);
        verify(authorRepository).deleteById(authorId);
    }


}
