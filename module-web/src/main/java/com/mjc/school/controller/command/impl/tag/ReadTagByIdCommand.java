package com.mjc.school.controller.command.impl.tag;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ConsoleReader;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class ReadTagByIdCommand implements Command {
    private final BaseController<TagDto, TagDto, Long> tagController;

    public ReadTagByIdCommand(BaseController<TagDto, TagDto, Long> tagController) {
        this.tagController = tagController;
    }

    @Override
    public void execute() {
        Long id;

        System.out.println("Please enter the tag's id:");
        id = ConsoleReader.readNumberFromUser();

        System.out.println(tagController.readById(id));
    }

    @Override
    public String getDescription() {
        return "Read tag by id";
    }
}
