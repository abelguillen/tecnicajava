package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;
import nz.net.ultraq.thymeleaf.LayoutDialect;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        TemplateEngine templateEngine = new TemplateEngine();
    	templateEngine.addDialect(new LayoutDialect());
        SpringApplication.run(Application.class, args);
    }
}