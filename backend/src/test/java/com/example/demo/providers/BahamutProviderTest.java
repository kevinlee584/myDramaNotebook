package com.example.demo.providers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import com.example.demo.model.Drama;
import com.example.demo.scraping.ScraperScripts;
import com.example.demo.service.ScraperService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

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
		MvcResult result =  mockMvc.perform(get("/providers"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].provider").value("bahamut"))
				.andExpect(jsonPath("$[0].favicon").exists())
				.andExpect(jsonPath("$[0].sorts").exists())
				.andReturn();

		String json = result.getResponse().getContentAsString();
		List<Map<String, Map<String, String>>> list = mapper.readValue(json, List.class);
		String url = list.get(0).get("sorts").entrySet().stream().findFirst().orElseThrow().getValue();

		result = mockMvc.perform(get(url))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].providerName").value("bahamut"))
				.andExpect(jsonPath("$[0].name").exists())
				.andExpect(jsonPath("$[0].imageUrl").exists())
				.andExpect(jsonPath("$[0].videoUrl").exists())
				.andReturn();

		json = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

		List<Map<String, String>> list2 = mapper.readValue(json, List.class);
		Map<String, String> m = list2.get(0);

		Drama drama = new Drama(m.get("providerName"), m.get("name"), m.get("imageUrl"), m.get("videoUrl"));
		String request = mapper.writeValueAsString(Map.of(
				"provider", drama.getProviderName(),
				"name", drama.getName()
		));

		mockMvc.perform(post("/user/save")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().isCreated())
				.andExpect(content().string(String.format("{ provider: %s, name: %s } saved", drama.getProviderName(), drama.getName())));

		mockMvc.perform(get("/user/record"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].providerName").value(drama.getProviderName()))
				.andExpect(jsonPath("$[0].name").value(drama.getName()))
				.andExpect(jsonPath("$[0].imageUrl").value(drama.getImageUrl()))
				.andExpect(jsonPath("$[0].videoUrl").value(drama.getVideoUrl()));

		mockMvc.perform(delete("/user/remove")
						.contentType(MediaType.APPLICATION_JSON)
						.content(request))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/user/record"))
				.andExpect(status().isOk())
				.andExpect(content().json("[]"));
	}


}
