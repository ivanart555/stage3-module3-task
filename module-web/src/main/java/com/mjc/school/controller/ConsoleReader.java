package com.mjc.school.controller;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleReader {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static Long readNumberFromUser() {
        return Long.parseLong(SCANNER.nextLine());
    }

    public static String readStringFromUser() {
        return SCANNER.nextLine();
    }
}
