package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagDto;
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

import static com.mjc.school.service.exception.ServiceErrorCode.TAG_ID_DOES_NOT_EXIST;

@Service
public class TagServiceImpl implements BaseService<TagDto, TagDto, Long> {
    private static final Logger LOGGER = Logger.getLogger(TagServiceImpl.class.getName());
    private final BaseRepository<TagModel, Long> tagRepository;
    private final ValidatorInstance validator;
    private final ModelMapper mapper = Mappers.getMapper(ModelMapper.class);

    public TagServiceImpl(BaseRepository<TagModel, Long> tagRepository, ValidatorInstance validator) {
        this.tagRepository = tagRepository;
        this.validator = validator;
    }

    @Override
    public List<TagDto> readAll() {
        return mapper.tagModelListToDtoList(tagRepository.readAll());
    }

    @Override
    public TagDto readById(Long tagId) {
        TagModel foundTag = tagRepository.readById(tagId).orElseThrow(
                () -> new ServiceException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), tagId)));

        return mapper.tagModelToDto(foundTag);
    }

    @Override
    public TagDto create(TagDto createRequest) {
        validate(createRequest);

        TagModel tag = mapper.tagDtoToModel(createRequest);
        TagModel createdTag = tagRepository.create(tag);

        return mapper.tagModelToDto(createdTag);
    }

    @Override
    public TagDto update(TagDto updateRequest) {
        validate(updateRequest);

        TagModel existingTag = tagRepository.readById(updateRequest.id()).orElseThrow(
                () -> new ServiceException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), updateRequest.id())));
        existingTag.setName(updateRequest.name());

        TagModel updatedTag = tagRepository.update(existingTag);

        return mapper.tagModelToDto(updatedTag);
    }

    @Override
    public boolean deleteById(Long tagId) {
        if (tagRepository.existById(tagId)) {
            return tagRepository.deleteById(tagId);
        } else {
            throw new ServiceException(String.format(TAG_ID_DOES_NOT_EXIST.getMessage(), tagId));
        }
    }

    private void validate(TagDto tagDto) throws ValidationException {
        Set<ConstraintViolation<TagDto>> violations = validator.getVALIDATOR().validate(tagDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<TagDto> violation : violations) {
                LOGGER.warning("Tag " + violation.getPropertyPath() + ": " + violation.getMessage());
            }
            throw new ValidationException("Failed to validate Tag");
        }
    }
}
