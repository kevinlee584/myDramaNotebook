package com.example.demo.service;

import com.example.demo.model.Drama;
import com.example.demo.scraping.Scraper;
import com.example.demo.scraping.ScraperScripts;
import com.example.demo.utils.Tuple;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
@Service
public class ScraperService {
    private Capabilities cap;
    private long expire;
    private final Map<String, Tuple<Instant, List<Drama>>> dramasCache =  new HashMap<>();

    public ScraperService(
            @Value("${webScrape.cache.expires}") long expire,
            @Autowired Capabilities cap
            ) {
        this.expire = expire;
        this.cap = cap;
    }

    synchronized public List<Drama> scrape(String provider, String sort) {

        String url = String.format("/provider/%s/%s", provider, sort);
        Tuple<Instant, List<Drama>> list = dramasCache.get(url);
        if (list != null && Duration.between(Instant.now(), list.x).toMillis() < expire) return list.y;

        Optional<Scraper> scraper = ScraperScripts.scrapers.stream().filter(s -> s.getProvider().getName().equals(provider)).findFirst();
        if (scraper.isEmpty()) return null;

        Function<WebDriver, List<Drama>> script = scraper.get().getScripts().get(sort);
        if (Objects.isNull(script)) return null;

        WebDriver driver = new RemoteWebDriver(cap);
        var result =script.apply(driver);
        driver.quit();

        dramasCache.put(url, new Tuple<>(Instant.now(), result));
        return result;
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
}
