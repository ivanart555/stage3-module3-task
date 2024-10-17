package com.mjc.school.controller.command.impl.tag;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ConsoleReader;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class CreateTagCommand implements Command {
    private final BaseController<TagDto, TagDto, Long> tagController;

    public CreateTagCommand(BaseController<TagDto, TagDto, Long> tagController) {
        this.tagController = tagController;
    }

    @Override
    public void execute() {
        String name;

        System.out.println("Please enter tag's name:");
        name = ConsoleReader.readStringFromUser();

        TagDto tagDto = new TagDto(null, name);

        System.out.println(tagController.create(tagDto));
    }

    @Override
    public String getDescription() {
        return "Create Tag";
    }
}
