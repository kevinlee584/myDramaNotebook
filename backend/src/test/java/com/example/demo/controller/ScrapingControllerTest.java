package com.example.demo.controller;

import com.example.demo.model.Drama;
import com.example.demo.service.ScraperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@WebMvcTest(ScrapingController.class)
class ScrapingControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ScraperService scraperService;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldReturnEmptyProvider() throws Exception {

        when(scraperService.getAllScraper()).thenReturn(Collections.EMPTY_LIST);

        mvc.perform(get("/providers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    public void shouldGetDramas() throws Exception {
        List<Drama> res = List.of(new Drama("test", "test", "testUrl", "testUrl"));

        when(scraperService.scrape("test", "test")).thenReturn(res);

        mvc.perform(get("/provider/test/test"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(res)));

    }

}