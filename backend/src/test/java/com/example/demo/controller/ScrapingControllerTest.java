package com.example.demo.controller;

import com.example.demo.service.ScraperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

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

        when(scraperService.getAllScraper()).thenReturn(Collections.EMPTY_MAP);

        mvc.perform(get("/providers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

}