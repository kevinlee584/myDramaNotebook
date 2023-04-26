package com.example.demo.scraping;

import com.example.demo.model.Drama;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface Scraper {
    Map<String, Function<ChromeDriver, List<Drama>>> getScripts();
}
