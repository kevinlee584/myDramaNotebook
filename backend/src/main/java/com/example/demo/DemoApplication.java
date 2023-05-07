package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		
		init();

		SpringApplication.run(DemoApplication.class, args);
	}

	private static void init() {
		try {
			
			loadScrapingScript();

		}catch(Exception ignore){}
	}

	private static void loadScrapingScript() throws ClassNotFoundException {
        Class.forName("com.example.demo.scraping.Bahamut");
    }

}
