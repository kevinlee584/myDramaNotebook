package com.example.demo.service;

import com.example.demo.model.Drama;
import com.example.demo.repo.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class UserService {

    final private List<Drama> record;
    final private RecordRepository recordRepository;

    final private ScraperService scraperService;

    public UserService(
            @Autowired ScraperService scraperService,
            @Autowired RecordRepository recordRepository
    ){
        this.scraperService = scraperService;
        this.recordRepository = recordRepository;
        record = recordRepository.getRecord();
    }

    public String saveDrama(String providerName, String dramaName){
        Drama drama = scraperService.getDrama(providerName, dramaName);

        if (Objects.isNull(drama)) return "not found";

        Drama d = new Drama(providerName, dramaName, "", "");
        Optional<Drama> dd = record.stream().filter(d::equals).findAny();

        if (dd.isPresent()) return "has saved";

        recordRepository.addDrama(drama);
        return "saved";
    }

    public void removeDrama(String providerName, String dramaName){
        recordRepository.removeDrama(providerName, dramaName);
    }

    public List<Drama> getRecord(){
        return record;
    }

}
