package com.example.demo.service;

import com.example.demo.model.Drama;
import com.example.demo.model.Provider;
import com.example.demo.scraping.Scraper;
import com.example.demo.scraping.ScraperScripts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

class ScraperServiceTest {
    private ScraperService scraperService;

    public ScraperServiceTest(){
        scraperService = new ScraperService(30_000, new ChromeOptions());
    }
    @BeforeAll
    public static void setup(){
        Drama drama = new Drama("test", "test", "testUrl", "testUrl");
        Function<WebDriver, List<Drama>> script = _driver -> List.of(drama);
        ScraperScripts.scrapers.add(new Scraper() {
            @Override
            public Map<String, Function<WebDriver, List<Drama>>> getScripts() {
                return Map.of("test", script);
            }

            @Override
            public Function<String, Function<WebDriver, List<Drama>>> getSearchScript() {
                return null;
            }

            @Override
            public Provider getProvider() {
                return new Provider("test", "testUrl");
            }
        });
    }

    @BeforeAll
    public static void clean(){
        ScraperScripts.scrapers.clear();
    }

    @Test
    void getDrama() {
        scraperService.scrape("test", "test");
        Drama d = scraperService.getDrama("test", "test");

        Assertions.assertNotEquals(d, null);
        Assertions.assertEquals(d.getName(), "test");
        Assertions.assertEquals(d.getProviderName(), "test");
        Assertions.assertEquals(d.getImageUrl(), "testUrl");
        Assertions.assertEquals(d.getVideoUrl(), "testUrl");
    }
}