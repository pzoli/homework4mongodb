package hu.infokristaly.homework4mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Homework4mongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(Homework4mongodbApplication.class, args);
	}

}
