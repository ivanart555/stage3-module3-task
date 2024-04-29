package com.mjc.school.controller.command.impl.author;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ConsoleReader;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class CreateAuthorCommand implements Command {
    private final BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController;

    public CreateAuthorCommand(BaseController<AuthorDtoRequest, AuthorDtoResponse, Long> authorController) {
        this.authorController = authorController;
    }

    @Override
    public void execute() {
        String name;

        System.out.println("Please enter author's name:");
        name = ConsoleReader.readStringFromUser();

        AuthorDtoRequest authorDto = new AuthorDtoRequest(null, name);

        System.out.println(authorController.create(authorDto));
    }

    @Override
    public String getDescription() {
        return "Create Author";
    }
}
