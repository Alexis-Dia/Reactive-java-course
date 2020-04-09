package example24;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;

public class ClientUploadApp {

	private static Logger logger = LoggerFactory.getLogger(ClientUploadApp.class);

	public static void main(String[] args) {

		WebClient client = WebClient.create("http://localhost:8080");

		Flux<Car> cars = Flux.interval(Duration.ofSeconds(10)).map(i -> new Car(i + 200,  new Location(new BigDecimal(1), new BigDecimal(1))));

		client.post()
				.uri("/cars")
				.contentType(MediaType.APPLICATION_STREAM_JSON)
				.body(cars, Car.class)
				.retrieve()
				.bodyToFlux(Car.class)
				.doOnNext(car -> logger.info("Uploaded: " + car))
				.blockLast();
	}

}
