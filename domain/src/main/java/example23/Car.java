package example23;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Car {

	private final Long id;

	private final Location location;


	@JsonCreator
	public Car(@JsonProperty("id") Long id, @JsonProperty("location") Location location) {
		this.id = id;
		this.location = location;
	}


	public Long getId() {
		return id;
	}

	public Location getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "Car{id=" + id + ", location=" + location + '}';
	}
}
