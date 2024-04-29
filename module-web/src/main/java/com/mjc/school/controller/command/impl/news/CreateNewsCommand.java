package com.mjc.school.controller.command.impl.news;

import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ConsoleReader;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.stereotype.Component;

@Component
public class CreateNewsCommand implements Command {
    private final BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController;

    public CreateNewsCommand(BaseController<NewsDtoRequest, NewsDtoResponse, Long> newsController) {
        this.newsController = newsController;
    }

    @Override
    public void execute() {
        String title;
        String content;
        Long authorId;

        System.out.println("Please enter the news title:");
        title = ConsoleReader.readStringFromUser();

        System.out.println("Please enter the news content:");
        content = ConsoleReader.readStringFromUser();

        System.out.println("Please enter the author's ID:");
        authorId = ConsoleReader.readNumberFromUser();

        NewsDtoRequest newsDto = new NewsDtoRequest(null, title, content, authorId);

        System.out.println(newsController.create(newsDto));
    }

    @Override
    public String getDescription() {
        return "Create News";
    }
}
