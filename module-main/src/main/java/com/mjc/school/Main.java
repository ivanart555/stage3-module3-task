package com.mjc.school;

import com.mjc.school.controller.NewsMenu;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaAuditing
@PropertySource("classpath:application.properties")
public class Main {

    public static void main(String[] args) {
//        SpringApplication.run(Main.class, args);
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        NewsMenu menu = context.getBean(NewsMenu.class);

        menu.displayMenu();
    }
}
