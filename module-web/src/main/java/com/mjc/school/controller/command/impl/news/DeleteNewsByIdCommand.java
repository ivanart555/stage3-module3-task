package com.mjc.school.controller.command.impl.news;


import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ConsoleReader;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class DeleteNewsByIdCommand implements Command {
    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;

    public DeleteNewsByIdCommand(BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController) {
        this.newsController = newsController;
    }

    @Override
    public void execute() {
        Long id;

        System.out.println("Please enter the news id to delete:");
        id = ConsoleReader.readNumberFromUser();

        if (newsController.deleteById(id)) {
            System.out.printf("News with id %d deleted.%n", id);
        }
    }

    @Override
    public String getDescription() {
        return "Delete News by id";
    }
}