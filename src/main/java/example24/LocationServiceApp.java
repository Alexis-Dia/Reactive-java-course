package example24;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;

@SpringBootApplication
public class LocationServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(LocationServiceApp.class, args);
	}

	@Bean
	public CommandLineRunner initData(MongoOperations mongo) {
		return (String... args) -> {

			mongo.dropCollection(Car.class);
			mongo.createCollection(Car.class, CollectionOptions.empty().size(1000000).capped());

		};
	}

}