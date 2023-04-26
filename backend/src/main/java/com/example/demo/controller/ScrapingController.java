package com.example.demo.controller;

import com.example.demo.model.Drama;
import com.example.demo.model.DramaSort;
import com.example.demo.model.Provider;
import com.example.demo.scraping.Bahamut;
import com.example.demo.scraping.Scraper;
import com.example.demo.scraping.ScraperScripts;
import com.example.demo.service.ScraperService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
class ProvidersResponseTemplate {
     final private String provider;
     final private Map<String, String> sorts;
}

@RestController
@AllArgsConstructor
public class ScrapingController {

    final private ScraperService scraperService;

    final private List<Provider> provides = List.of(
            new Provider("Bahamut")
    );

    final private List<DramaSort> sortOfDramas = List.of(
            new DramaSort("new", "bahamut", "/provider/bahamut/new"),
            new DramaSort("hot", "bahamut", "/provider/bahamut/hot")
    );

    @PostConstruct
    void loadScrapingScript() throws ClassNotFoundException {
        Class.forName("com.example.demo.scraping.Bahamut");
    }

    final private List<ProvidersResponseTemplate> providersResponse = sortOfDramas.stream()
            .collect(Collectors.toMap(DramaSort::getProviderName,
                    e -> Map.of(e.getSortingName(), e.getSortingUrl()),
                    (m1, m2) -> Stream.of(m1, m2).flatMap(map -> map.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))))
            .entrySet().stream().map(e -> new ProvidersResponseTemplate(e.getKey(), e.getValue()))
            .collect(Collectors.toList());


    @GetMapping("/providers")
    public List<ProvidersResponseTemplate> getAllProviders(){
        return providersResponse;
    }

    @GetMapping("/provider/{provider}/{sort}")
    public List<Drama> getNewDramas(@PathVariable("provider") String provider, @PathVariable("sort") String sort){

        Optional<Function<ChromeDriver, List<Drama>>> result = sortOfDramas.stream()
                .filter(e -> e.getProviderName().equals(provider) && e.getSortingName().equals(sort))
                .map(e -> ScraperScripts.scrapers.get(provider).getScripts().get(sort))
                .findFirst();

        if (result.isPresent()) {
            return scraperService.scrape(result.get());
        } else {
          return  Collections.emptyList();
        }
    }

}
