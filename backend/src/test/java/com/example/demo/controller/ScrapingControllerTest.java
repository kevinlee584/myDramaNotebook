package com.example.demo.controller;

import com.example.demo.model.Drama;
import com.example.demo.service.ScraperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;


class ScrapingControllerTest {

    private MockMvc mvc;
    private ScraperService scraperService;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup(){
        scraperService = mock(ScraperService.class);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);
        mvc = MockMvcBuilders.standaloneSetup(new ScrapingController(scraperService)).setMessageConverters(converter).build();
    }

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
        Function<ChromeDriver, List<Drama>> fun =
                driver -> res;

        when(scraperService.scrape("test", "test")).thenReturn(res);

        mvc.perform(get("/provider/test/test"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(res)));

    }

}