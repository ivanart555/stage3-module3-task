package com.mjc.school.controller;

import com.mjc.school.controller.command.Command;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;

@Component
public class NewsMenu {
    private final Command[] commands;

    public NewsMenu(Command[] commands) {
        this.commands = commands;
        Arrays.sort(this.commands, Comparator.comparing(Command::getDescription));
    }

    public void displayMenu() {
        System.out.println("News Management Menu:");
        for (int i = 0; i < commands.length; i++) {
            System.out.println((i + 1) + ". " + commands[i].getDescription());
        }
        System.out.println("0. Exit");

        handleUsersChoice();
    }

    public void executeCommand(int choice) {
        if (choice > 0 && choice <= commands.length) {
            commands[choice - 1].execute();
        } else {
            System.out.println("Invalid choice. Please select a valid option.");
        }
    }

    private void handleUsersChoice() {
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            try {
                choice = ConsoleReader.readNumberFromUser().intValue();
                if (choice == 0) System.exit(0);

                executeCommand(choice);
            } catch (Exception e) {
                System.out.println("Invalid choice. Please select a valid option. " + e.getMessage());
            }
            displayMenu();
        }


    }
}
