package com.mjc.school.controller.command.impl.news;


import com.mjc.school.controller.BaseController;
import com.mjc.school.controller.ConsoleReader;
import com.mjc.school.controller.command.Command;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDto;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

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
        Set<Long> tagIds;

        System.out.println("Please enter the news id to edit:");
        id = ConsoleReader.readNumberFromUser();

        System.out.println("Please enter new news title:");
        title = ConsoleReader.readStringFromUser();

        System.out.println("Please enter new the news content:");
        content = ConsoleReader.readStringFromUser();

        System.out.println("Please enter new the author's ID:");
        authorId = ConsoleReader.readNumberFromUser();

        System.out.println("Please enter the tag IDs if needed (separated by coma):");
        tagIds = ConsoleReader.readDistinctNumbersFromUser();

        Set<TagDto> tagDtos = tagIds.stream()
                .map(tagId -> new TagDto(tagId, null))
                .collect(Collectors.toSet());

        NewsDtoRequest newsDto = new NewsDtoRequest(id, title, content, authorId, tagDtos);

        System.out.println(newsController.update(newsDto));
    }

    @Override
    public String getDescription() {
        return "Edit news by id";
    }
}