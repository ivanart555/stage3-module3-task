package com.mjc.school.controller.command.impl.author;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ConsoleReader;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class DeleteAuthorByIdCommand implements Command {
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;

    public DeleteAuthorByIdCommand(BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController) {
        this.authorController = authorController;
    }

    @Override
    public void execute() {
        Long id;

        System.out.println("Please enter the author's id to delete:");
        id = ConsoleReader.readNumberFromUser();

        if (authorController.deleteById(id)) {
            System.out.printf("Author with id %d deleted.%n", id);
        }
    }

    @Override
    public String getDescription() {
        return "Delete Author by id";
    }
}
