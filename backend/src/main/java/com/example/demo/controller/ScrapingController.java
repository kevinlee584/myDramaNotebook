package com.example.demo.controller;

import com.example.demo.model.Drama;
import com.example.demo.scraping.ScraperScripts;
import com.example.demo.service.ScraperService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getDramas(@PathVariable("provider") String provider, @PathVariable("sort") String sort){

        Optional<Function<ChromeDriver, List<Drama>>> result = ScraperScripts.scrapers.entrySet().stream()
                .filter(e -> e.getKey().equals(provider))
                .map(e -> e.getValue().getScripts().get(sort))
                .filter(Objects::nonNull)
                .findFirst();

        if (result.isPresent()) {
            String url = String.format("/provider/{%s}/{%s}", provider, sort);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(scraperService.scrape(url, result.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Resource not found, provider: %s, sort: %s", provider, sort ));
        }
    }

}