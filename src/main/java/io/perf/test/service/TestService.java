package io.perf.test.service;

import java.util.List;

import io.perf.test.model.Location;
import io.perf.test.model.LocationWithStringIds;

public interface TestService {

	void importWithSpringTransactionWithJdbcSpecs(List<Location> locations);

	void importWithMyBatisBatchWithJdbcSpecs(List<Location> locations);

	void importWithSpringTransactionWithoutJdbcSpecs(List<Location> locations);

	void importWithMyBatisBatchWithoutJdbcSpecs(List<Location> locations);

	void importWithMyBatisBatchWithStringIds(List<LocationWithStringIds> locations);

}
