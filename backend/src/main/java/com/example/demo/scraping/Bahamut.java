package com.example.demo.scraping;


import com.example.demo.model.Drama;
import com.example.demo.model.Provider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


final public class Bahamut implements Scraper{

    final private String url = "https://ani.gamer.com.tw";

    final private Provider provider = new Provider("bahamut", url + "/favicon.ico");


    static {
        ScraperScripts.scrapers.add(new Bahamut());
    }

    private List<Drama> getNewDramas(WebDriver driver) {
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("anime-block")));

        final WebElement block = driver.findElement(By.className("newanime-block"));

        Document document = Jsoup.parse(block.getAttribute("innerHTML"));
        Elements animates = document.select(".anime-block");

        return animates.stream().map(e -> {
            try {
                String animeName = e.selectFirst(".anime-name > p").text();
                String animePicUrl = e.selectFirst(".anime-blocker > img").attr("data-src");
                String animeVideoUrl = e.selectFirst(".anime-card-block").attr("href");

                if (!animePicUrl.startsWith("http"))
                    animePicUrl = String.format("%s/%s", url, animePicUrl);
                if (!animeVideoUrl.startsWith("http"))
                    animeVideoUrl = String.format("%s/%s", url, animeVideoUrl);

                return new Drama("bahamut", animeName, animePicUrl, animeVideoUrl);
            } catch (NullPointerException error) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private List<Drama> getHotDramas(WebDriver driver) {
        driver.get(url);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("blockHotAnime")));

        final WebElement block = driver.findElement(By.id("blockHotAnime")).findElement(By.className("theme-list-block"));
        Document document = Jsoup.parse(block.getAttribute("innerHTML"));
        Elements animates = document.select(".theme-list-main");

        return animates.stream().map(e -> {
            try {
                String animeName = e.selectFirst(".theme-info-block > p").text();
                String animePicUrl = e.selectFirst("img").attr("data-src");
                String animeVideoUrl = e.attr("href");

                if (!animePicUrl.startsWith("http"))
                    animePicUrl = String.format("%s/%s", url, animePicUrl);
                if (!animeVideoUrl.startsWith("http"))
                    animeVideoUrl = String.format("%s/%s", url, animeVideoUrl);

                return new Drama("bahamut", animeName, animePicUrl, animeVideoUrl);
            }catch (NullPointerException error) {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private Function<WebDriver, List<Drama>> searchDramas(String keyword) {

        return driver -> {
            driver.get(url);

            WebElement search = driver.findElement(By.id("anime-search-sky"));
            search.sendKeys(keyword, Keys.ENTER);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            wait.until(driver1 -> !driver1.getCurrentUrl().equals(url));

            Document document = Jsoup.parse(driver.findElement(By.className("theme-list-block")).getAttribute("innerHTML"));
            Elements animates = document.select(".theme-list-main");

            if (animates.size() == 0) {
                return Collections.emptyList();
            }

            return animates.stream().map(e -> {
                try {
                    String animeName = e.selectFirst(".theme-info-block > p").text();
                    String animePicUrl = e.selectFirst("img").attr("data-src");
                    String animeVideoUrl = e.attr("href");

                    if (!animePicUrl.startsWith("http"))
                        animePicUrl = String.format("%s/%s", url, animePicUrl);
                    if (!animeVideoUrl.startsWith("http"))
                        animeVideoUrl = String.format("%s/%s", url, animeVideoUrl);

                    return new Drama("bahamut", animeName, animePicUrl, animeVideoUrl);
                }catch (NullPointerException error) {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
        };
    }

    @Override
    public Map<String, Function<WebDriver, List<Drama>>> getScripts() {
        return Map.of(
                "new", this::getNewDramas,
                "hot", this::getHotDramas
        );
    }

    @Override
    public Function<String, Function<WebDriver, List<Drama>>> getSearchScript() {
        return this::searchDramas;
    }

    @Override
    public Provider getProvider() {
        return provider;
    }
}
