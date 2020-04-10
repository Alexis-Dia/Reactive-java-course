package example23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@SpringBootApplication
public class RequestServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(RequestServiceApp.class, args);
	}

	@Bean
	public RouterFunction<?> routes(CarRequestHandler handler) {
		return RouterFunctions.route(POST("/cars/{id}/booking"), handler::createBooking);
	}

}
