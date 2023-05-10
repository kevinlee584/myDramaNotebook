package com.example.demo.service;

import com.example.demo.model.Drama;
import com.example.demo.model.Provider;
import com.example.demo.scraping.Scraper;
import com.example.demo.scraping.ScraperScripts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;


class ScraperServiceTest {

    private final long expire = 30_000;
    private ScraperService scraperService;
    private Function<ChromeDriver, List<Drama>> script;

    @BeforeEach
    public void setup(){
        scraperService = new ScraperService(expire, null, null);
        script = Mockito.mock(Function.class);
    }

    @Test
    void getDrama() {
        ScraperScripts.scrapers.add(new Scraper() {
            @Override
            public Map<String, Function<ChromeDriver, List<Drama>>> getScripts() {
                return Map.of("test", script);
            }

            @Override
            public Provider getProvider() {
                return new Provider("test", "testUrl");
            }
        });
        Mockito.when(script.apply(any()))
                        .thenReturn(List.of(new Drama("test", "test", "testUrl", "testUrl")));

        scraperService.scrape("test", "test");
        Drama d = scraperService.getDrama("test", "test");

        Assertions.assertNotEquals(d, null);
        Assertions.assertEquals(d.getName(), "test");
        Assertions.assertEquals(d.getProviderName(), "test");
        Assertions.assertEquals(d.getImageUrl(), "testUrl");
        Assertions.assertEquals(d.getVideoUrl(), "testUrl");

        ScraperScripts.scrapers = new LinkedList<>();
    }
}