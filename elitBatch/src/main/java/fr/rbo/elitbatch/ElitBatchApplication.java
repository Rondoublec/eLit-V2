package fr.rbo.elitbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients("fr.rbo.elitbatch")
@SpringBootApplication
public class ElitBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElitBatchApplication.class, args);
	}

}
