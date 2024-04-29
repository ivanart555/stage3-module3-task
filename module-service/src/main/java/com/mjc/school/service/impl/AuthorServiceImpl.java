package com.mjc.school.service.impl;


import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exception.ServiceException;
import com.mjc.school.service.mapper.ModelMapper;
import com.mjc.school.service.validator.ValidatorInstance;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import static com.mjc.school.service.exception.ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST;

@Service
public class AuthorServiceImpl implements BaseService<AuthorDtoRequest, AuthorDtoResponse, Long> {
    private static final Logger LOGGER = Logger.getLogger(AuthorServiceImpl.class.getName());
    private final BaseRepository<AuthorModel, Long> authorRepository;
    private final ValidatorInstance Validator;
    private final ModelMapper mapper = Mappers.getMapper(ModelMapper.class);

    public AuthorServiceImpl(BaseRepository<AuthorModel, Long> authorRepository, ValidatorInstance validator) {
        this.authorRepository = authorRepository;
        Validator = validator;
    }

    @Override
    public List<AuthorDtoResponse> readAll() {
        return mapper.authorModelListToDtoList(authorRepository.readAll());
    }

    @Override
    public AuthorDtoResponse readById(Long authorId) {
        Optional<AuthorModel> foundAuthor = authorRepository.readById(authorId);
        if (foundAuthor.isEmpty()) {
            throw new ServiceException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), authorId));
        }
        return mapper.authorModelToDto(foundAuthor.get());
    }

    @Override
    public AuthorDtoResponse create(AuthorDtoRequest createRequest) {
        validate(createRequest);
        AuthorModel author = mapper.authorDtoToModel(createRequest);
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        author.setCreateDate(date);
        author.setLastUpdateDate(date);
        AuthorModel createdAuthor = authorRepository.create(author);
        return mapper.authorModelToDto(createdAuthor);
    }

    @Override
    public AuthorDtoResponse update(AuthorDtoRequest updateRequest) {
        validate(updateRequest);
        if (authorRepository.existById(updateRequest.id())) {
            AuthorModel author = mapper.authorDtoToModel(updateRequest);
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
            author.setLastUpdateDate(date);
            AuthorModel updatedAuthor = authorRepository.update(author);
            return mapper.authorModelToDto(updatedAuthor);
        } else {
            throw new ServiceException(
                    String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id()));
        }
    }

    @Override
    public boolean deleteById(Long authorId) {
        if (authorRepository.existById(authorId)) {
            return authorRepository.deleteById(authorId);
        } else {
            throw new ServiceException(String.format(AUTHOR_ID_DOES_NOT_EXIST.getMessage(), authorId));
        }
    }

    private void validate(AuthorDtoRequest authorDto) throws ValidationException {
        Set<ConstraintViolation<AuthorDtoRequest>> violations = Validator.getVALIDATOR().validate(authorDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<AuthorDtoRequest> violation : violations) {
                LOGGER.warning("Author " + violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new ValidationException("Failed to validate Author");
        }
    }
}
