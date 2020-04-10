package example23;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
public class CarLocationController {

	private final CarRepository repository;
	List<Integer> elements = new ArrayList<>();

	public CarLocationController(CarRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/cars")
	public Flux<Car> getCars() {
		return this.repository.findAll().log();
	}

/*	@GetMapping(path = "/cars", produces = "application/stream+json")
	public Flux<Car> getCarStream() {
		return this.repository.findCarsBy().log();
	}*/

	// WebFlux only

	@PostMapping(path="/cars", consumes = "application/stream+json")
	@ResponseStatus(HttpStatus.CREATED)
	public Flux<Car> loadCars(@RequestBody Flux<Car> cars) {
		Flux<Car> carPublisher = this.repository.insert(cars);
		CountDownLatch countDownLatch = new CountDownLatch(1);
		//carPublisher.doOnNext(System.out::println);
		carPublisher
				.doOnComplete(countDownLatch::countDown);
				//.subscribe(System.out::println);
/*		carPublisher.subscribe((s) -> {
			System.out.println("asdasdasd");
		});*/

		return carPublisher.log();
	}

}
