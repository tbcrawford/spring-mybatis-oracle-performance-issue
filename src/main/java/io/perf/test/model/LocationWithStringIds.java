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
public class LocationWithStringIds {

	// IDs
	private String company1Id;
	private String company2Id;
	private String company3Id;
	private String company4Id;
	private String company5Id;

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
