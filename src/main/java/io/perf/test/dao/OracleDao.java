package io.perf.test.dao;

import io.perf.test.model.Location;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OracleDao {

	void deleteLocations();

	void insertLocationWithJdbcTypeSpecs(Location location);

	void insertLocationWithoutJdbcTypeSpecs(Location location);

}
