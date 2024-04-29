package com.mjc.school.controller.command.impl.author;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ConsoleReader;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class UpdateAuthorByIdCommand implements Command {
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;

    public UpdateAuthorByIdCommand(BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController) {
        this.authorController = authorController;
    }

    @Override
    public void execute() {
        Long id;
        String name;

        System.out.println("Please enter the author's id to edit:");
        id = ConsoleReader.readNumberFromUser();

        System.out.println("Please enter new author's name:");
        name = ConsoleReader.readStringFromUser();

        AuthorDtoRequest authorDto = new AuthorDtoRequest(id, name);

        System.out.println(authorController.update(authorDto));
    }

    @Override
    public String getDescription() {
        return "Edit author by id";
    }
}
