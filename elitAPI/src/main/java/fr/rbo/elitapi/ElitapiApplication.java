package fr.rbo.elitapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElitapiApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ElitapiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ElitapiApplication.class, args);
	}

}
