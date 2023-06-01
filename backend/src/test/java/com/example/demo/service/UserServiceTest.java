package com.example.demo.service;

import com.example.demo.model.Drama;
import com.example.demo.repo.RecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;
    private ScraperService scraperService;

    public UserServiceTest(){
        scraperService = Mockito.mock(ScraperService.class);
        RecordRepository recordRepository = Mockito.mock(RecordRepository.class);
        userService = new UserService(scraperService, recordRepository);
    }
    @Test
    void saveDrama() {
        Drama drama = new Drama("test", "test", "testUrl", "testUrl");
        Mockito.when(scraperService.getDrama("test", "test")).thenReturn(drama);
        String result = userService.saveDrama("test", "test");

        assertEquals(result, "saved");
    }
}