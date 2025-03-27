package com.telran.springpractice;

import com.telran.springpractice.bean.Language;
import com.telran.springpractice.service.CardService;
import org.springframework.boot.ApplicationContextFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
//@Configuration
//@ComponentScan
//@PropertySource(value = "application.properties")
public class SpringPracticeApplication {

    public static void main(String[] args) {
//        SpringApplication.run(SpringPracticeApplication.class, args);

        ApplicationContext context = SpringApplication.run(SpringPracticeApplication.class, args);

        Language language = context.getBean(Language.class);
        System.out.println(language);
        CardService bean = context.getBean(CardService.class);
        bean.print();
    }

}
