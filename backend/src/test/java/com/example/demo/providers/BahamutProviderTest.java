package com.example.demo.providers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.example.demo.model.Drama;
import com.example.demo.scraping.Scraper;
import com.example.demo.scraping.ScraperScripts;
import com.example.demo.service.ScraperService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class BahamutProviderTest{

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ScraperService scraperService;

	@Autowired
	private ObjectMapper mapper;


	@BeforeAll
	public static void setup() throws ClassNotFoundException {
		Class.forName("com.example.demo.scraping.Bahamut");
	}

	@AfterAll
	public static void clean(){
		ScraperScripts.scrapers.clear();
	}

	@Test
	public void ShouldSaveAndRemoveDrama() throws Exception {
		mockMvc.perform(get("/providers"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].provider").value("bahamut"))
				.andExpect(jsonPath("$[0].favicon").exists());


		Scraper scraper = scraperService.getAllScraper().get("bahamut");
		Optional<Map.Entry<String, Function<ChromeDriver, List<Drama>>>> result =
				scraper.getScripts().entrySet().stream().findFirst();

		Assertions.assertTrue(result.isPresent());

		Map.Entry<String, Function<ChromeDriver, List<Drama>>> p = result.get();
		Drama drama= scraperService.scrape("/provider/bahamut/" + p.getKey(), p.getValue()).get(0);
		Map<String, String> m = Map.of("provider", drama.getProviderName(), "name", drama.getName());
		String json = mapper.writeValueAsString(m);

		mockMvc.perform(post("/user/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated())
				.andExpect(content().json(json));

		mockMvc.perform(get("/user/record"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].providerName").value(drama.getProviderName()))
				.andExpect(jsonPath("$[0].name").value(drama.getName()))
				.andExpect(jsonPath("$[0].imageUrl").value(drama.getImageUrl()))
				.andExpect(jsonPath("$[0].videoUrl").value(drama.getVideoUrl()));

		mockMvc.perform(delete("/user/remove")
						.contentType(MediaType.APPLICATION_JSON)
						.content(json))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/user/record"))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}


}
