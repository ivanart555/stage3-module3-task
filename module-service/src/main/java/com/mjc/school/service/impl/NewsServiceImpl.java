package com.mjc.school.service.impl;


import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exception.ServiceException;
import com.mjc.school.service.mapper.ModelMapper;
import com.mjc.school.service.validator.ValidatorInstance;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static com.mjc.school.service.exception.ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST;

@Service
public class NewsServiceImpl implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {
    private static final Logger LOGGER = Logger.getLogger(NewsServiceImpl.class.getName());
    private final BaseRepository<NewsModel, Long> newsRepository;
    private final ValidatorInstance validator;
    private final ModelMapper mapper = Mappers.getMapper(ModelMapper.class);

    public NewsServiceImpl(BaseRepository<NewsModel, Long> newsRepository,
                           ValidatorInstance validator) {
        this.newsRepository = newsRepository;
        this.validator = validator;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return mapper.newsModelListToDtoList(newsRepository.readAll());
    }

    @Override
    public NewsDtoResponse readById(Long newsId) {
        NewsModel foundNews = newsRepository.readById(newsId).orElseThrow(
                () -> new ServiceException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), newsId)));

        return mapper.newsModelToDto(foundNews);
    }

    @Override
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        validate(createRequest);

        NewsModel news = mapper.newsDtoToModel(createRequest);
        NewsModel createdNews = newsRepository.create(news);

        return mapper.newsModelToDto(createdNews);
    }

    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        validate(updateRequest);

        NewsModel newsToUpdate = mapper.newsDtoToModel(updateRequest);

        NewsModel updatedNews = newsRepository.update(newsToUpdate);

        return mapper.newsModelToDto(updatedNews);
    }

    @Override
    public boolean deleteById(Long newsId) {
        if (newsRepository.existById(newsId)) {
            return newsRepository.deleteById(newsId);
        } else {
            throw new ServiceException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), newsId));
        }
    }

    private void validate(NewsDtoRequest newsDto) throws ValidationException {
        Set<ConstraintViolation<NewsDtoRequest>> violations = validator.getVALIDATOR().validate(newsDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<NewsDtoRequest> violation : violations) {
                LOGGER.warning("News " + violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new ValidationException("Failed to validate News");
        }
    }
}
