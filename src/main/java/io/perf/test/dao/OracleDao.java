package io.perf.test.dao;

import io.perf.test.model.Location;
import io.perf.test.model.LocationWithStringIds;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OracleDao {

	void deleteLocations();

	void deleteLocationsWithStringIds();

	void insertLocationWithJdbcTypeSpecs(Location location);

	void insertLocationWithoutJdbcTypeSpecs(Location location);

	void insertLocationWithVarcharIds(LocationWithStringIds location);

}
