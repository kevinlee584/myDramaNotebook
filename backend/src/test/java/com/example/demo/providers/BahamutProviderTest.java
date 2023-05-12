package com.example.demo.providers;


import java.util.List;

import com.example.demo.scraping.ScraperScripts;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class BahamutProviderTest extends ProviderTestBase{

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	public BahamutProviderTest(
			@Autowired MockMvc mockMvc,
			@Autowired ObjectMapper mapper
	){
		super(mapper, mockMvc, "bahamut", List.of("new", "hot"));
	}

	@BeforeAll
	public static void setup() throws ClassNotFoundException {
		Class.forName("com.example.demo.scraping.Bahamut");
	}

	@AfterAll
	public static void clean(){
		ScraperScripts.scrapers.clear();
	}

	@Override
	@Test
	public void providerAndSortShouldExist() throws Exception {
		super.providerAndSortShouldExist();
	}

	@Override
	@Test
	public void shouldHaveDramas() throws Exception {
		super.shouldHaveDramas();
	}
}
