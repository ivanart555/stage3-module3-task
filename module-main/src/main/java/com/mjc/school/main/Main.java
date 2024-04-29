package com.mjc.school.main;

import com.mjc.school.controller.NewsMenu;
import com.mjc.school.main.config.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        NewsMenu menu = context.getBean(NewsMenu.class);

        menu.displayMenu();
    }
}
