package com.example.demo.config;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfiguration {

    public SeleniumConfiguration(@Value("${webdriver.remote.server}") String url) {
        System.setProperty("webdriver.remote.server", url);
    }
    @Bean
    public Capabilities driverCapabilities() {
        DesiredCapabilities cap=new DesiredCapabilities();
        cap.setBrowserName("chrome");
        cap.setPlatform(Platform.LINUX);

        ChromeOptions options = new ChromeOptions();
        options.merge(cap);
        options.setHeadless(true);

        return options;
    }
}
