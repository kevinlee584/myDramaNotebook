package com.example.demo.configuration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    public ChromeDriver driver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--headless");
        return new ChromeDriver(options);
    }

}
