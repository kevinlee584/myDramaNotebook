package com.example.demo.service;

import com.example.demo.model.Drama;
import lombok.AllArgsConstructor;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
@AllArgsConstructor

class Tuple<X, Y> {
    public final X x;
    public final Y y;
}
@Service
public class ScraperService {

    private final ChromeOptions options;
    private ChromeDriver driver ;

    @Value("${webScrape.cache.expires}")
    private long expire;
    private final Map<String, Tuple<Instant, List<Drama>>> dramasCache =  new HashMap<>();

    public ScraperService(@Autowired ChromeOptions options) {
        this.options = options;
        driver = new ChromeDriver(options);
    }

    synchronized public List<Drama> scrape(String url, Function<ChromeDriver, List<Drama>> scraper) {

        var list = dramasCache.get(url);

        if (list == null || Duration.between(Instant.now(), list.x).toMillis() > expire) {

            WebDriverException error = null;

            for (int i=0; i<2; ++i) {
                try {
                    var result =scraper.apply(driver);
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

    @PreDestroy
    public void destroy() {
        driver.quit();
    }

}
