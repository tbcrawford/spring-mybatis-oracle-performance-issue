package io.perf.test.service;

public interface TimerService {

	void conductTests();

	void testSpringTransactionWithJdbcTypeSpecs();

	void testMyBatisBatchWithJdbcTypeSpecs();

	void testSpringTransactionWithoutJdbcTypeSpecs();

	void testMyBatisBatchWithoutJdbcTypeSpecs();

	void testMyBatisBatchWithStringIds();

}
