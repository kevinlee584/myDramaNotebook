package com.example.demo.controller;

import com.example.demo.model.Drama;
import com.example.demo.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

        providers = scraperService.getAllScraper().stream()
                .map(e -> {
                    String providerName = e.getProvider().getName();
                    String providerFavoriteIcon = e.getProvider().getFaviconUrl();
                    Map<String, String> providerSorts =  e.getScripts().keySet().stream()
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

        List<Drama> result = scraperService.scrape(provider, sort);

        if (Objects.nonNull(result)) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Resource not found, provider: %s, sort: %s", provider, sort ));
        }
    }

}