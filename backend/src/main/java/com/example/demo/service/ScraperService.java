package com.example.demo.service;

import com.example.demo.model.Drama;
import com.example.demo.scraping.Scraper;
import com.example.demo.scraping.ScraperScripts;
import com.example.demo.utils.Tuple;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
@Service
public class ScraperService {

    private ChromeOptions options;
    private ChromeDriver driver ;
    private long expire;
    private final Map<String, Tuple<Instant, List<Drama>>> dramasCache =  new HashMap<>();

    public ScraperService(
            @Value("${webScrape.cache.expires}") long expire,
            @Autowired ChromeOptions options,
            @Autowired ChromeDriver driver
    ) {
        this.expire = expire;
        this.driver = driver;
        this.options = options;
    }

    synchronized public List<Drama> scrape(String provider, String sort) {

        String url = String.format("/provider/%s/%s", provider, sort);
        Tuple<Instant, List<Drama>> list = dramasCache.get(url);

        if (list == null || Duration.between(Instant.now(), list.x).toMillis() > expire) {

            Optional<Scraper> scraper = ScraperScripts.scrapers.stream().filter(s -> s.getProvider().getName().equals(provider)).findFirst();
            if (scraper.isEmpty()) return null;

            Function<ChromeDriver, List<Drama>> script = scraper.get().getScripts().get(sort);
            if (Objects.isNull(script)) return null;

            WebDriverException error = null;

            for (int i=0; i<2; ++i) {
                try {
                    var result =script.apply(driver);
                    dramasCache.put(url, new Tuple<>(Instant.now(), result));
                    return result;

                }catch (WebDriverException e) {
                    error = e;
                    driver.quit();
                    driver = new ChromeDriver(options);
                }
            }

            throw error;
        }

        return list.y;

    }

    public Drama getDrama(String provider, String drama) {

        Optional<Drama> result =  dramasCache.entrySet().stream()
                .filter(e -> e.getKey().startsWith(String.format("/provider/%s", provider)))
                .flatMap(e -> e.getValue().y.stream())
                .filter(e -> e.getName().equals(drama))
                .findFirst();

        return result.orElse(null);
    }

    public List<? extends Scraper> getAllScraper() {
        return ScraperScripts.scrapers;
    }

    @PreDestroy
    public void destroy() {
        driver.quit();
    }

}
