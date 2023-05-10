package com.example.demo.providers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class BahamutProviderTest{

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	public void getAllProviders() throws Exception {

		Class.forName("com.example.demo.scraping.Bahamut");
		
		Map<String, String> map = new HashMap<>();
		map.put("new", "/provider/bahamut/new");
		map.put("hot", "/provider/bahamut/hot");

		List<Map<String, Object>> mockResponse = List.of(
			Map.of(
					"provider", "bahamut",
					"favicon", "https://ani.gamer.com.tw/favicon.ico",
					"sorts", map)
		);

		String expectedResponseContent = mapper.writeValueAsString(mockResponse);

		mockMvc.perform(get("/providers"))
				.andExpect(status().isOk());
	}


}
