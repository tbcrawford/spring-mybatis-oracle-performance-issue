package io.perf.test.service;

import java.util.List;

import javax.annotation.PostConstruct;

import io.perf.test.generator.DataGenerator;
import io.perf.test.model.Location;

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

	@PostConstruct
	public void init() {
		largeList = dataGenerator.getLargeLocationList();
	}

	@Override
	public void conductTests() {
		testSpringTransactionWithJdbcTypeSpecs();
		testMyBatisBatchWithJdbcTypeSpecs();

		testSpringTransactionWithoutJdbcTypeSpecs();
		testMyBatisBatchWithoutJdbcTypeSpecs();
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

}
