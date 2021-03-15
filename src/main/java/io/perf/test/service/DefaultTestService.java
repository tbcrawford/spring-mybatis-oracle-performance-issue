package io.perf.test.service;

import java.util.List;

import io.perf.test.dao.OracleDao;
import io.perf.test.model.Location;
import io.perf.test.model.LocationWithStringIds;
import io.perf.test.util.OraBatchExecutor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultTestService implements TestService {

	private final OracleDao oracleDao;

	@Override
	@Transactional(rollbackFor = Exception.class, transactionManager = "oraTransactionManager")
	public void importWithSpringTransactionWithJdbcSpecs(List<Location> locations) {
		oracleDao.deleteLocations();
		locations.forEach(oracleDao::insertLocationWithJdbcTypeSpecs);
	}

	@Override
	public void importWithMyBatisBatchWithJdbcSpecs(List<Location> locations) {
		OraBatchExecutor.batch(OracleDao.class, batchDao -> {
			batchDao.deleteLocations();
			locations.forEach(batchDao::insertLocationWithJdbcTypeSpecs);
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class, transactionManager = "oraTransactionManager")
	public void importWithSpringTransactionWithoutJdbcSpecs(List<Location> locations) {
		oracleDao.deleteLocations();
		locations.forEach(oracleDao::insertLocationWithoutJdbcTypeSpecs);
	}

	@Override
	public void importWithMyBatisBatchWithoutJdbcSpecs(List<Location> locations) {
		OraBatchExecutor.batch(OracleDao.class, batchDao -> {
			batchDao.deleteLocations();
			locations.forEach(batchDao::insertLocationWithoutJdbcTypeSpecs);
		});
	}

	@Override
	public void importWithMyBatisBatchWithStringIds(List<LocationWithStringIds> locations) {
		OraBatchExecutor.batch(OracleDao.class, batchDao -> {
			batchDao.deleteLocationsWithStringIds();
			locations.forEach(batchDao::insertLocationWithVarcharIds);
		});
	}

}
