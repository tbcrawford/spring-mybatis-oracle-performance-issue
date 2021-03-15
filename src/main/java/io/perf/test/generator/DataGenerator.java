package io.perf.test.generator;

import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import io.perf.test.model.Location;
import io.perf.test.model.LocationWithStringIds;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static io.perf.test.util.AppUtils.asDate;
import static io.perf.test.util.AppUtils.asLocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataGenerator {

	private static final int LARGE_LIST_SIZE = 10_000;
	private static final int MEDIUM_LIST_SIZE = 5000;
	private static final int SMALL_LIST_SIZE = 500;
	private static int LARGE_RANDOM_SIZE;

	private static final LocalDateTime now = LocalDateTime.now();
	private static final String LOCATION_TYPE = "T";

	private final Faker faker;

	@PostConstruct
	void init() {
		LARGE_RANDOM_SIZE = faker.number().numberBetween(7_345, 12_345);
	}

	public List<Location> getLargeLocationList() {
		return IntStream.range(0, LARGE_LIST_SIZE)
				.mapToObj(this::getLocation)
				.collect(Collectors.toList());
	}

	public List<LocationWithStringIds> getLargeLocationWithStringIdsList() {
		return IntStream.range(0, LARGE_LIST_SIZE)
				.mapToObj(this::getLocationWithStringIds)
				.collect(Collectors.toList());
	}

	public List<Location> getMediumLocationList() {
		return IntStream.range(0, MEDIUM_LIST_SIZE)
				.mapToObj(this::getLocation)
				.collect(Collectors.toList());
	}

	public List<Location> getSmallLocationList() {
		return IntStream.range(0, SMALL_LIST_SIZE)
				.mapToObj(this::getLocation)
				.collect(Collectors.toList());
	}

	private Location getLocation(int i) {
		String location = RandomStringUtils.randomAscii(15, 30);
		String name = i % 7 == 0 ? faker.funnyName().name() + "'s #" + i : faker.funnyName().name() + "#" + i;
		String stateAbbr = faker.address().stateAbbr();

		int company3Id = faker.number().numberBetween(10, 15_000);
		int company5Id = faker.number().numberBetween(400_000, 10_000_000);
		String company4Id = i % 10 == 0 ? null : stateAbbr + i;

		return Location.builder()
				.company1Id(faker.number().numberBetween(10, 15_000))
				.company2Id(faker.number().numberBetween(10, 15_000))
				.company3Id(company3Id % 3 == 0 ? null : company3Id)
				.company4Id(company4Id)
				.company5Id(company5Id % 3 == 0 ? null : company5Id)
				.name(name)
				.address(faker.address().streetAddress())
				.location(location)
				.city(faker.address().city())
				.state(stateAbbr)
				.zipcode(faker.address().zipCode())
				.latitude(new BigDecimal(faker.address().latitude()))
				.longitude(new BigDecimal(faker.address().longitude()))
				.fuelPrice(faker.number().randomDouble(3, 2, 4))
				.fuelPriceDate(asLocalDateTime(
						faker.date().between(
								asDate(now.minusDays(3)), asDate(now))
						)
				)
				.locationType(LOCATION_TYPE)
				.build();
	}

	private LocationWithStringIds getLocationWithStringIds(int i) {
		String location = RandomStringUtils.randomAscii(15, 30);
		String name = i % 7 == 0 ? faker.funnyName().name() + "'s #" + i : faker.funnyName().name() + "#" + i;
		String stateAbbr = faker.address().stateAbbr();

		int company3Id = faker.number().numberBetween(10, 15_000);
		int company5Id = faker.number().numberBetween(400_000, 10_000_000);
		String company4Id = i % 10 == 0 ? null : stateAbbr + i;

		return LocationWithStringIds.builder()
				.company1Id(String.valueOf(faker.number().numberBetween(10, 15_000)))
				.company2Id(String.valueOf(faker.number().numberBetween(10, 15_000)))
				.company3Id(String.valueOf(company3Id % 3 == 0 ? null : company3Id))
				.company4Id(company4Id)
				.company5Id(String.valueOf(company5Id % 3 == 0 ? null : company5Id))
				.name(name)
				.address(faker.address().streetAddress())
				.location(location)
				.city(faker.address().city())
				.state(stateAbbr)
				.zipcode(faker.address().zipCode())
				.latitude(new BigDecimal(faker.address().latitude()))
				.longitude(new BigDecimal(faker.address().longitude()))
				.fuelPrice(faker.number().randomDouble(3, 2, 4))
				.fuelPriceDate(asLocalDateTime(
						faker.date().between(
								asDate(now.minusDays(3)), asDate(now))
						)
				)
				.locationType(LOCATION_TYPE)
				.build();
	}

}
