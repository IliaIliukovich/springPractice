package com.telran.springpractice.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Language {

    @Value("${language.name}")
    private String name;
    @Value("${language.code}")
    private String code;


    @Override
    public String toString() {
        return "Language{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
