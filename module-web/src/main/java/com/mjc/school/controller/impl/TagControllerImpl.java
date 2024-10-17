package com.mjc.school.controller.impl;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.annotation.CommandBody;
import com.mjc.school.controller.annotation.CommandHandler;
import com.mjc.school.controller.annotation.CommandParam;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.TagDto;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TagControllerImpl implements BaseController<TagDto, TagDto, Long> {
    private final BaseService<TagDto, TagDto, Long> tagService;

    public TagControllerImpl(BaseService<TagDto, TagDto, Long> tagService) {
        this.tagService = tagService;
    }

    @Override
    @CommandHandler("readAllTags")
    public List<TagDto> readAll() {
        return tagService.readAll();
    }

    @Override
    @CommandHandler("readTagById")
    public TagDto readById(@CommandParam Long tagId) {
        return tagService.readById(tagId);
    }

    @Override
    @CommandHandler("createTag")
    public TagDto create(@CommandBody TagDto createRequest) {
        return tagService.create(createRequest);
    }

    @Override
    @CommandHandler("updateTag")
    public TagDto update(@CommandBody TagDto updateRequest) {
        return tagService.update(updateRequest);
    }

    @Override
    @CommandHandler("deleteTag")
    public boolean deleteById(@CommandParam Long tagId) {
        return tagService.deleteById(tagId);
    }
}
