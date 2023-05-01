package com.example.demo.service;

import com.example.demo.model.Drama;
import lombok.AllArgsConstructor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.function.Function;

@Service
public class ScraperService {

    private final ChromeOptions options;
    private ChromeDriver driver ;

    public ScraperService(@Autowired ChromeOptions options) {
        this.options = options;
    }

    @PostConstruct
    void postConstruct() {
        driver = new ChromeDriver(options);
    }

    synchronized public List<Drama> scrape(Function<ChromeDriver, List<Drama>> scraper) {
        WebDriverException error = null;

        for (int i = 0; i < 2; ++i) {
            try {
                return scraper.apply(driver);
            }catch (WebDriverException e) {
                error = e;
                driver.quit();
                driver = new ChromeDriver(options);
            }
        }

        throw error;
    }

    @PreDestroy
    public void destroy() {
        driver.quit();
    }

}
