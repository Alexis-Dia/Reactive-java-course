package example23;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import reactor.core.publisher.Flux;

import java.time.Duration;

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

			LocationGenerator gen = new LocationGenerator(90, 90);
			Flux.range(1, 100)
				//.map(i -> new Car(1l, new Location(new BigDecimal(90), new BigDecimal(90))))
				.map(i -> new Car(i.longValue(), gen.location()))
				.doOnNext(mongo::save)
				.blockLast(Duration.ofSeconds(5));
		};
	}

}