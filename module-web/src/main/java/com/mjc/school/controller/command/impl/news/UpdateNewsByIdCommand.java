package com.mjc.school.controller.command.impl.news;


import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ConsoleReader;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class UpdateNewsByIdCommand implements Command {
    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;

    public UpdateNewsByIdCommand(BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController) {
        this.newsController = newsController;
    }

    @Override
    public void execute() {
        Long id;
        String title;
        String content;
        Long authorId;

        System.out.println("Please enter the news id to edit:");
        id = ConsoleReader.readNumberFromUser();

        System.out.println("Please enter new news title:");
        title = ConsoleReader.readStringFromUser();

        System.out.println("Please enter new the news content:");
        content = ConsoleReader.readStringFromUser();

        System.out.println("Please enter new the author's ID:");
        authorId = ConsoleReader.readNumberFromUser();

        NewsDtoRequest newsDto = new NewsDtoRequest(id, title, content, authorId);

        System.out.println(newsController.update(newsDto));
    }

    @Override
    public String getDescription() {
        return "Edit news by id";
    }
}