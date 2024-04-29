package com.mjc.school.repository.utils;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private static final String AUTHORS_FILENAME = "authors";
    private static final String CONTENT_FILENAME = "content";
    private static final String NEWS_FILENAME = "news";
    private static final int STARTING_YEAR = 2022;
    private static final int NEWS_COUNT_TO_GENERATE = 20;
    private static final Random random = new Random();

    private DataGenerator() {
    }

    public static List<NewsModel> generateNews() {
        List<String> contentLines = ContentReader.getFileContent(CONTENT_FILENAME);
        List<String> titlesLines = ContentReader.getFileContent(NEWS_FILENAME);

        List<AuthorModel> authors = generateAuthors();

        List<NewsModel> newsList = new ArrayList<>();

        for (int i = 1; i < NEWS_COUNT_TO_GENERATE + 1; i++) {
            NewsModel news = new NewsModel();
            news.setId((long) i);
            news.setAuthorId((long) random.nextInt(1, authors.size()));
            news.setTitle(getRandomLine(titlesLines));
            news.setContent(getRandomLine(contentLines));
            news.setCreateDate(generateRandomLocalDateTime());
            news.setLastUpdateDate(LocalDateTime.now());
            newsList.add(news);
        }

        return newsList;


    }

    public static List<AuthorModel> generateAuthors() {
        List<AuthorModel> authors = new ArrayList<>();

        List<String> authorsLines = ContentReader.getFileContent(AUTHORS_FILENAME);
        long counter = 1L;
        for (String element : authorsLines) {
            authors.add(new AuthorModel(counter, element, generateRandomLocalDateTime(), LocalDateTime.now()));
            counter++;
        }

        return authors;
    }

    private static String getRandomLine(List<String> content) {
        return content.get(random.nextInt(content.size() - 1));
    }

//    private static Integer getRandomId(int min, int max) {
//        return random.nextInt()
//    }

    private static LocalDateTime generateRandomLocalDateTime() {
        if (STARTING_YEAR < 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        LocalDateTime startDate = LocalDateTime.of(STARTING_YEAR, 1, 1, 0, 0);

        long randomMonths = random.nextInt(12);

        LocalDateTime randomDateTime = startDate.plusMonths(randomMonths);

        randomDateTime = randomDateTime.plusHours(random.nextInt(24))
                .plusMinutes(random.nextInt(60))
                .plusSeconds(random.nextInt(60));

        return randomDateTime;
    }

}
