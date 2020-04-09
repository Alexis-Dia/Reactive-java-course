package example24;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CarLocationController {

	private final CarRepository repository;
	List<Integer> elements = new ArrayList<>();

	public CarLocationController(CarRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/cars")
	public Flux<Car> getCars() {
		/*This line returns streaming values if we enter  ->  curl -v -H "Accept:text/event-stream" http://localhost:8081/cars */
		return this.repository.findCarsBy().log();
	}

	@PostMapping(path="/cars", consumes = "application/stream+json")
	@ResponseStatus(HttpStatus.CREATED)
	public Flux<Car> loadCars(@RequestBody Flux<Car> cars) {
		Flux<Car> carPublisher = this.repository.insert(cars);

		return carPublisher.log();
	}

}
