package com.example.demo.providers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.demo.config.SeleniumConfiguration;
import com.example.demo.scraping.ScraperScripts;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

class BahamutProviderTest extends ProviderTestBase{

	private Capabilities cap;
	public BahamutProviderTest() throws IOException {
		super("bahamut", List.of("new", "hot"));

		Properties pros = new Properties();
		ClassLoader loader = BahamutProviderTest.class.getClassLoader();
		InputStream input = loader.getResourceAsStream("application.properties");
		pros.load(input);

		String server = pros.getProperty("webdriver.remote.server");
		Matcher matcher = Pattern.compile("^\\$\\{(.+?):(.+)}$").matcher(server);

		if (matcher.find() && matcher.groupCount() == 2) {
			if (Objects.nonNull(System.getenv(matcher.group(1)))) {
				server = System.getenv(matcher.group(1));
			}else {
				server = matcher.group(2);
			}
		}
		this.cap = new SeleniumConfiguration(server).driverCapabilities();
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

	@Test
	public void shouldHaveDramas() throws Exception {
		WebDriver driver = new RemoteWebDriver(cap);
		super.shouldHaveDramas(driver);
		driver.quit();
	}
}
