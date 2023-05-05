package com.example.demo.controller;

import com.example.demo.model.Drama;
import com.example.demo.scraping.ScraperScripts;
import com.example.demo.service.ScraperService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
class ProvidersResponseTemplate {
     final private String provider;
     final private String favicon;
     final private Map<String, String> sorts;
}

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
public class ScrapingController {

    final private ScraperService scraperService;

    private List<ProvidersResponseTemplate> getProviders() {
        return ScraperScripts.scrapers.entrySet().stream()
                .map(e -> {
                    String providerName = e.getKey();
                    String providerFavoriteIcon = e.getValue().getProvider().getFaviconUrl();
                    Map<String, String> providerSorts =  e.getValue().getScripts().keySet().stream()
                            .collect(Collectors.toMap(e2 -> e2, e2 ->"/provider/" + providerName + "/" + e2));
                    return new ProvidersResponseTemplate(providerName, providerFavoriteIcon, providerSorts);
                }).collect(Collectors.toList());
    }

    @GetMapping("/providers")
    public List<ProvidersResponseTemplate> getAllProviders(){
        return getProviders();
    }

    @GetMapping("/provider/{provider}/{sort}")
    public List<Drama> getNewDramas(@PathVariable("provider") String provider, @PathVariable("sort") String sort){

        Optional<Function<ChromeDriver, List<Drama>>> result = ScraperScripts.scrapers.entrySet().stream()
                .filter(e -> e.getKey().equals(provider))
                .map(e -> e.getValue().getScripts().get(sort))
                .findFirst();

        if (result.isPresent()) {
            String url = String.format("/provider/{%s}/{%s}", provider, sort);
            return scraperService.scrape(url, result.get());
        } else {
          return  Collections.emptyList();
        }
    }

}
