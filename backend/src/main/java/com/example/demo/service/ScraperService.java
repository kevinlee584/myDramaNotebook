package com.example.demo.service;

import com.example.demo.model.Drama;
import lombok.AllArgsConstructor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class ScraperService {

    private final ChromeDriver driver;

    public List<Drama> scrape(Function<ChromeDriver, List<Drama>> scraper) {
        return scraper.apply(driver);
    }

    @PreDestroy
    public void destroy() {
        driver.quit();
    }

}
