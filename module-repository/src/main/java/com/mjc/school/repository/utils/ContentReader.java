package com.mjc.school.repository.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ContentReader {
    private static final Logger LOGGER = Logger.getLogger(ContentReader.class.getName());

    private ContentReader() {
    }

    public static List<String> getFileContent(String fileName) {
        List<String> content = new ArrayList<>();

        String absolutePath = "/" + fileName;

        try (InputStream inputStream = ContentReader.class.getResourceAsStream(absolutePath)) {
            if (inputStream == null) {
                throw new FileNotFoundException(fileName);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    content.add(line);
                }

            }
        } catch (IOException e) {
            LOGGER.warning("Failed to read file: " + fileName);
        }

        return content;
    }
}
