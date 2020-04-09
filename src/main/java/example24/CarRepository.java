package example24;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface CarRepository extends ReactiveMongoRepository<Car, Long> {

	@Tailable
	Flux<Car> findCarsBy();

}
