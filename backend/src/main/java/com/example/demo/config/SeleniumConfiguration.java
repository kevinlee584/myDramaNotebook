package com.example.demo.config;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class SeleniumConfiguration {

    @PostConstruct
    void postConstruct() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/kevin/bin/chromedriver.exe");
    }

    @Bean
    public ChromeOptions options() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        return options;
    }

    @Bean
    public ChromeDriver driver(@Autowired ChromeOptions options) {
        return new ChromeDriver(options);
    }

}
