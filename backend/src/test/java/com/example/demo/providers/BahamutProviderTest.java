package com.example.demo.providers;


import java.util.List;

import com.example.demo.scraping.ScraperScripts;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Capabilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BahamutProviderTest extends ProviderTestBase{

	public BahamutProviderTest(@Autowired Capabilities cap){
		super("bahamut", List.of("new", "hot"), cap);
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
