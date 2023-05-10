package com.example.demo.service;

import com.example.demo.model.Drama;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class UserService {

    final private List<Drama> record = new LinkedList<>();

    final private ScraperService scraperService;

    public UserService(
            @Autowired ScraperService scraperService
    ){
        this.scraperService = scraperService;
    }

    public String saveDrama(String providerName, String dramaName){
        Drama drama = scraperService.getDrama(providerName, dramaName);
        if (Objects.nonNull(drama)) {
            Optional<Drama> d = record.stream()
                    .filter(e -> e.getProviderName().equals(providerName) && e.getName().equals(dramaName))
                    .findAny();

            if (d.isEmpty()) {
                record.add(drama);
                return "saved";
            }

            return "has saved";
        }

        return "not found";
    }

    public void removeDrama(String providerName, String dramaName){
        for (int i=0; i<record.size(); ++i) {
            if (record.get(i).getName().equals(dramaName) && record.get(i).getProviderName().equals(providerName)){
                record.remove(i);
                return;
            }
        }
    }

    public List<Drama> getRecord(){
        return record;
    }

}
