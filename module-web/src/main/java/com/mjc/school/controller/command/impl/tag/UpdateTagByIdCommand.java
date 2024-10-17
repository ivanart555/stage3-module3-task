package com.mjc.school.controller.command.impl.tag;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ConsoleReader;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class UpdateTagByIdCommand implements Command {
    private final BaseController<TagDto, TagDto, Long> tagController;

    public UpdateTagByIdCommand(BaseController<TagDto, TagDto, Long> tagController) {
        this.tagController = tagController;
    }

    @Override
    public void execute() {
        Long id;
        String name;

        System.out.println("Please enter the tag's id to edit:");
        id = ConsoleReader.readNumberFromUser();

        System.out.println("Please enter new tag's name:");
        name = ConsoleReader.readStringFromUser();

        TagDto tagDto = new TagDto(id, name);

        System.out.println(tagController.update(tagDto));
    }

    @Override
    public String getDescription() {
        return "Edit tag by id";
    }
}
