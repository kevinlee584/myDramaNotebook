package com.example.demo.scraping;

import com.example.demo.model.Drama;
import com.example.demo.model.Provider;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface Scraper {
    Map<String, Function<WebDriver, List<Drama>>> getScripts();

    Function<String, Function<WebDriver, List<Drama>>> getSearchScript();

    Provider getProvider();

}
