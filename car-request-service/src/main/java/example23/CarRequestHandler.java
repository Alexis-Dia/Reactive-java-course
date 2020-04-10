package example23;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;
import java.util.Random;

@Component
public class CarRequestHandler {

	private static final Random random = new Random();

	public Mono<ServerResponse> createBooking(ServerRequest request) {
		return Mono.delay(randomThinkTime())
				.then(ServerResponse.created(bookingUrl(request)).build());
	}

	/**
	 * Simulate driver accepting the request after "think time" of 2-5 secs.
	 */
	private static Duration randomThinkTime() {
		return Duration.ofSeconds(random.nextInt(5 - 2) + 2);
	}

	private static URI bookingUrl(ServerRequest request) {
		Long id = Long.valueOf(request.pathVariable("id"));
		return URI.create("/car/" + id + "/booking/" + Math.abs(random.nextInt()));
	}

}
