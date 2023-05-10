package com.example.demo.service;

import com.example.demo.model.Drama;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    private ScraperService scraperService;

    @BeforeEach
    public void setup(){
        scraperService = Mockito.mock(ScraperService.class);
        userService = new UserService(scraperService);
    }

    @Test
    void saveDrama() {

        Mockito.when(scraperService.getDrama("test", "test"))
                .thenReturn(new Drama("test", "test", "testUrl", "testUrl"));

        userService.saveDrama("test", "test");

        assertEquals(userService.getRecord().size(), 1);
    }
}