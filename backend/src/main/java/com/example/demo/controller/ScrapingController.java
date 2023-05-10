package com.example.demo.controller;

import com.example.demo.model.Drama;
import com.example.demo.service.ScraperService;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ScrapingController {

    final private ScraperService scraperService;
    private List<Map<String, Object>> providers;

    public ScrapingController(
            @Autowired ScraperService scraperService
    ){
        this.scraperService = scraperService;

        providers = scraperService.getAllScraper().entrySet().stream()
                .map(e -> {
                    String providerName = e.getKey();
                    String providerFavoriteIcon = e.getValue().getProvider().getFaviconUrl();
                    Map<String, String> providerSorts =  e.getValue().getScripts().keySet().stream()
                            .collect(Collectors.toMap(e2 -> e2, e2 ->"/provider/" + providerName + "/" + e2));
                    return Map.of("provider", providerName,
                            "favicon", providerFavoriteIcon,
                            "sorts", providerSorts);
                }).collect(Collectors.toList());
    }

    @GetMapping("/providers")
    public List<Map<String, Object>> getAllProviders(){
        return providers;
    }

    @GetMapping("/provider/{provider}/{sort}")
    public ResponseEntity<?> getDramas(@PathVariable("provider") String provider, @PathVariable("sort") String sort){

        Optional<Function<ChromeDriver, List<Drama>>> result = scraperService.getAllScraper().entrySet().stream()
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