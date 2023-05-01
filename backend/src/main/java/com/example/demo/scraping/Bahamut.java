package com.example.demo.scraping;


import com.example.demo.model.Drama;
import com.example.demo.model.Provider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


final public class Bahamut implements Scraper{

    final private String url = "https://ani.gamer.com.tw";

    final private Provider provider = new Provider("bahamut", url + "/favicon.ico");


    static {
        ScraperScripts.scrapers.put("bahamut", new Bahamut());
    }

    private List<Drama> getNewDramas(ChromeDriver driver) {
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("anime-block")));

        final WebElement words = driver.findElement(By.className("newanime-block"));
        final List<WebElement> animates = words.findElements(By.className("anime-block"));

        return animates.stream().map(e -> {
            String animeName = e.findElement(By.className("anime-name"))
                    .findElement(By.tagName("p")).getAttribute("textContent");
            String animePicUrl = e.findElement(By.className("anime-blocker"))
                    .findElement(By.tagName("img")).getAttribute("data-src");
            String animeVideoUrl = e.findElement(By.className("anime-card-block"))
                    .getAttribute("href");


            return new Drama("Bahamut", animeName, animePicUrl, animeVideoUrl);
        }).collect(Collectors.toList());
    }

    private List<Drama> getHotDramas(ChromeDriver driver) {
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("blockHotAnime")));

        final WebElement words = driver.findElement(By.id("blockHotAnime")).findElement(By.className("theme-list-block"));
        final List<WebElement> animates = words.findElements(By.className("theme-list-main"));

        return animates.stream().map(e -> {
            String animeName = e.findElement(By.className("theme-info-block"))
                    .findElement(By.tagName("p")).getAttribute("textContent");
            String animePicUrl = e.findElement(By.tagName("img")).getAttribute("data-src");
            String animeVideoUrl = e.getAttribute("href");


            return new Drama("Bahamut", animeName, animePicUrl, animeVideoUrl);
        }).collect(Collectors.toList());
    }

    @Override
    public Map<String, Function<ChromeDriver, List<Drama>>> getScripts() {
        return Map.of(
                "new", this::getNewDramas,
                "hot", this::getHotDramas
        );
    }

    @Override
    public Provider getProvider() {
        return provider;
    }
}
