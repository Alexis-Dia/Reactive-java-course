package example23;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CarController {

	private static final Logger logger = LoggerFactory.getLogger(CarController.class);

	private final WebClient carsClient = WebClient.create("http://localhost:8081");

	private final WebClient bookClient = WebClient.create("http://localhost:8082");

	@PostMapping("/booking2")
	public Flux<Car> book2() {
		logger.debug("Processing booking request..");
		return carsClient.get().uri("/cars")
				.retrieve()
				.bodyToFlux(Car.class)
				.doOnNext(car -> logger.info("Trying to book " + car))
				.take(5);
	}

	@PostMapping("/booking3")
	public Mono<Car> book3() {
		logger.debug("Processing booking request..");
		return carsClient.get().uri("/cars")
				.retrieve()
				.bodyToFlux(Car.class)
				.doOnNext(car -> logger.info("Trying to book " + car))
				.take(5).next();
	}

	@PostMapping("/booking")
	public Mono<ResponseEntity<Void>> book() {
		logger.debug("Processing booking request..");
		return carsClient.get().uri("/cars")
				.retrieve()
				.bodyToFlux(Car.class)
				.doOnNext(car -> logger.info("Trying to book " + car))
				.take(5)
				.flatMap(this::requestCar)
				.next()
				.doOnNext(car -> logger.info("Booked car " + car));
	}

	private Mono<ResponseEntity<Void>> requestCar(Car car) {
		return bookClient.post()
				.uri("/cars/{id}/booking", car.getId())
				.exchange()
				.flatMap(response -> {return response.toEntity(Void.class);});
	}

}
