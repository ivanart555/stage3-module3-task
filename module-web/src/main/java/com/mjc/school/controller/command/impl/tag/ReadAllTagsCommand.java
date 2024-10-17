package com.mjc.school.controller.command.impl.tag;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class ReadAllTagsCommand implements Command {
    private final BaseController<TagDto, TagDto, Long> tagController;

    public ReadAllTagsCommand(BaseController<TagDto, TagDto, Long> tagController) {
        this.tagController = tagController;
    }

    @Override
    public void execute() {
        tagController.readAll().forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "Read all Tags";
    }
}
