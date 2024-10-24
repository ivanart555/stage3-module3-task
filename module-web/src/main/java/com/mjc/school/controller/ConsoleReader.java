package com.mjc.school.controller;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ConsoleReader {
    private static final Scanner SCANNER = new Scanner(System.in);

    private ConsoleReader() {
    }

    public static Long readNumberFromUser() {
        return Long.parseLong(SCANNER.nextLine());
    }

    public static Set<Long> readDistinctNumbersFromUser() {
        String input = SCANNER.nextLine();

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toSet());
    }

    public static String readStringFromUser() {
        return SCANNER.nextLine();
    }
}
