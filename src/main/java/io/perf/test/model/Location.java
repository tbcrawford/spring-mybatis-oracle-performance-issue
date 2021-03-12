package io.perf.test.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {

	// IDs
	private Integer company1Id;
	private Integer company2Id;
	private Integer company3Id;
	private String company4Id;
	private Integer company5Id;

	// Location Details
	private String name;
	private String address;
	private String location;
	private String city;
	private String state;
	private String zipcode;

	// Geolocation
	private BigDecimal latitude;
	private BigDecimal longitude;

	// Fuel
	private Double fuelPrice;
	private LocalDateTime fuelPriceDate;

	// About
	private String locationType;

}
