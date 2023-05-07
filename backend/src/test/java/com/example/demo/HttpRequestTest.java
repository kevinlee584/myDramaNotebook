package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.ProvidersResponseTemplate;
import com.example.demo.scraping.ScraperScripts;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class HttpRequestTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void getEmptyProviderList() throws Exception{

		ScraperScripts.scrapers.clear();	

		mockMvc.perform(get("/providers")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().json("[]"));

	}

	@Test
	void getAllProviders() throws Exception{

		Class.forName("com.example.demo.scraping.Bahamut");
		
		Map<String, String> map = new HashMap<>();

		map.put("new", "/provider/bahamut/new");
		map.put("hot", "/provider/bahamut/hot");
		List<ProvidersResponseTemplate> mockResponse = List.of(
			new ProvidersResponseTemplate(
				"bahamut", 
				"https://ani.gamer.com.tw/favicon.ico", 
				map)
		);

		String expectedResponseContent = mapper.writeValueAsString(mockResponse);

		mockMvc.perform(get("/providers")).andDo(print()).andExpect(status().isOk())
			.andExpect(content().json(expectedResponseContent));

	}


}
