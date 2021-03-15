package io.perf.test.service;

import java.util.List;

import javax.annotation.PostConstruct;

import io.perf.test.generator.DataGenerator;
import io.perf.test.model.Location;
import io.perf.test.model.LocationWithStringIds;

import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static io.perf.test.util.AppUtils.toSeconds;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTimerService implements TimerService {

	private final TestService defaultTestService;
	private final DataGenerator dataGenerator;

	private static List<Location> largeList;
	private static List<LocationWithStringIds> largeListWithStringIds;

	@PostConstruct
	public void init() {
		largeList = dataGenerator.getLargeLocationList();
		largeListWithStringIds = dataGenerator.getLargeLocationWithStringIdsList();
	}

	@Override
	public void conductTests() {
		testSpringTransactionWithJdbcTypeSpecs();
		testMyBatisBatchWithJdbcTypeSpecs();

		testMyBatisBatchWithStringIds();
	}

	/**
	 * Tests:
	 * 1. 2.412s - 10,000 rows
	 * 2. 2.447s - 10,000 rows
	 */
	@Override
	public void testSpringTransactionWithJdbcTypeSpecs() {
		log.info("Starting Spring Transaction test with jdbcTypes specified");

		StopWatch watch = new StopWatch();
		watch.start();

		defaultTestService.importWithSpringTransactionWithJdbcSpecs(largeList);

		watch.stop();
		log.info("Spring Transaction elapsed time: {}s", toSeconds(watch.getTime()));
	}

	/**
	 * Tests:
	 * 1. 1.375s - 10,000 rows
	 * 2. 1.310s - 10,000 rows
	 */
	@Override
	public void testMyBatisBatchWithJdbcTypeSpecs() {
		log.info("Starting MyBatis Batch test with jdbcTypes specified");

		StopWatch watch = new StopWatch();
		watch.start();

		defaultTestService.importWithMyBatisBatchWithJdbcSpecs(largeList);

		watch.stop();
		log.info("MyBatis Batch elapsed time: {}s", toSeconds(watch.getTime()));
	}

	/**
	 * Tests:
	 * 1. 254.930s - 10,000 rows
	 *
	 * Due to the amount of time this takes to perform these tests, more test numbers aren't recorded here.
	 * More tests have been done in the past, and have shown the same approximate timings.
	 * These timings are far greater than the above tests when the jdbcType is set in the xml.
	 */
	@Override
	public void testSpringTransactionWithoutJdbcTypeSpecs() {
		log.info("Starting Spring Transaction test WITHOUT jdbcTypes specified");

		StopWatch watch = new StopWatch();
		watch.start();

		defaultTestService.importWithSpringTransactionWithoutJdbcSpecs(largeList);

		watch.stop();
		log.info("Spring Transaction elapsed time: {}s", toSeconds(watch.getTime()));
	}

	/**
	 * Tests:
	 * 1. 263.360s - 10,000 rows
	 *
	 * Due to the amount of time this takes to perform these tests, more test numbers aren't recorded here.
	 * More tests have been done in the past, and have shown the same approximate timings.
	 * These timings are far greater than the above tests when the jdbcType is set in the xml.
	 */
	@Override
	public void testMyBatisBatchWithoutJdbcTypeSpecs() {
		log.info("Starting MyBatis Batch test WITHOUT jdbcTypes specified");

		StopWatch watch = new StopWatch();
		watch.start();

		defaultTestService.importWithMyBatisBatchWithoutJdbcSpecs(largeList);

		watch.stop();
		log.info("MyBatis Batch elapsed time: {}s", toSeconds(watch.getTime()));
	}

	/**
	 * Tests:
	 * 1. 2.473s
	 * 2. 2.502s
	 * 3. 2.456s
	 */
	@Override
	public void testMyBatisBatchWithStringIds() {
		log.info("Starting MyBatis Batch test with STRING IDs");

		StopWatch watch = new StopWatch();
		watch.start();

		defaultTestService.importWithMyBatisBatchWithStringIds(largeListWithStringIds);

		watch.stop();
		log.info("MyBatis Batch with STRING IDs elapsed time: {}s", toSeconds(watch.getTime()));
	}

}
