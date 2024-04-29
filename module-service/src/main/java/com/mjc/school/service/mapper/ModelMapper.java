package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface ModelMapper {

    List<NewsDtoResponse> newsModelListToDtoList(List<NewsModel> newsModelList);

    NewsDtoResponse newsModelToDto(NewsModel newsModel);

    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true)
    })
    NewsModel newsDtoToModel(NewsDtoRequest newsModelRequest);

    List<AuthorDtoResponse> authorModelListToDtoList(List<AuthorModel> authorModelList);

    AuthorDtoResponse authorModelToDto(AuthorModel authorModel);

    @Mappings({
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "lastUpdateDate", ignore = true)
    })
    AuthorModel authorDtoToModel(AuthorDtoRequest authorModelRequest);
}