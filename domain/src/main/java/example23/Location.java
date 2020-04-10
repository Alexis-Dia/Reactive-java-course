package example23;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Location {

	private final BigDecimal longitude;

	private final BigDecimal latitude;


	@JsonCreator
	public Location(@JsonProperty("longitude") BigDecimal longitude,
			@JsonProperty("latitude") BigDecimal latitude) {

		this.longitude = longitude;
		this.latitude = latitude;
	}


	public BigDecimal getLongitude() {
		return longitude;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	@Override
	public String toString() {
		return "{" + longitude + ", " + latitude + "}";
	}
}
