package com.example.demo.service;

import com.example.demo.model.Drama;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.function.Function;


class ScraperServiceTest {

    private final long expire = 30_000;
    private ChromeDriver driver;
    private ScraperService scraperService;
    private Function<ChromeDriver, List<Drama>> script;

    @BeforeEach
    public void setup(){
        scraperService = new ScraperService(expire, null, driver);
        script = Mockito.mock(Function.class);
    }

    @Test
    void getDrama() {

        Mockito.when(script.apply(driver))
                        .thenReturn(List.of(new Drama("test", "test", "testUrl", "testUrl")));

        scraperService.scrape("/provider/test/test", script);
        Drama d = scraperService.getDrama("test", "test");

        Assertions.assertNotEquals(d, null);
        Assertions.assertEquals(d.getName(), "test");
        Assertions.assertEquals(d.getProviderName(), "test");
        Assertions.assertEquals(d.getImageUrl(), "testUrl");
        Assertions.assertEquals(d.getVideoUrl(), "testUrl");
    }
}