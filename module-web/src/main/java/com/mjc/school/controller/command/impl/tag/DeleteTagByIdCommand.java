package com.mjc.school.controller.command.impl.tag;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ConsoleReader;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.TagDto;
import org.springframework.stereotype.Component;

@Component
public class DeleteTagByIdCommand implements Command {
    private final BaseController<TagDto, TagDto, Long> tagController;

    public DeleteTagByIdCommand(BaseController<TagDto, TagDto, Long> tagController) {
        this.tagController = tagController;
    }

    @Override
    public void execute() {
        Long id;

        System.out.println("Please enter the tag's id to delete:");
        id = ConsoleReader.readNumberFromUser();

        if (tagController.deleteById(id)) {
            System.out.printf("Tag with id %d deleted.%n", id);
        }
    }

    @Override
    public String getDescription() {
        return "Delete Tag by id";
    }
}
