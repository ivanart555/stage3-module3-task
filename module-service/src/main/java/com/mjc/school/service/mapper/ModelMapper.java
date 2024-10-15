package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper
public interface ModelMapper {

    List<NewsDtoResponse> newsModelListToDtoList(List<NewsModel> newsModelList);


    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "createDate", expression = "java(formatDate(newsModel.getCreateDate()))")
    @Mapping(target = "lastUpdateDate", expression = "java(formatDate(newsModel.getLastUpdateDate()))")
    NewsDtoResponse newsModelToDto(NewsModel newsModel);

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "author.id", source = "authorId")
    NewsModel newsDtoToModel(NewsDtoRequest newsModelRequest);

    List<AuthorDtoResponse> authorModelListToDtoList(List<AuthorModel> authorModelList);

    @Mapping(target = "createDate", expression = "java(formatDate(authorModel.getCreateDate()))")
    @Mapping(target = "lastUpdateDate", expression = "java(formatDate(authorModel.getLastUpdateDate()))")
    AuthorDtoResponse authorModelToDto(AuthorModel authorModel);

    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    AuthorModel authorDtoToModel(AuthorDtoRequest authorModelRequest);

    default String formatDate(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS")) : null;
    }

}