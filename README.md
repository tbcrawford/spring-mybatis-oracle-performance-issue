# Spring MyBatis Oracle Performance Issues

## Overview
When inserting into an Oracle database via Spring MyBatis transactions or via MyBatis with ExecutorType.BATCH **WIHOUT** specifying the jdbcType in the xml for nullable columns, the performance can severly degrade and become 200x or more slower. This will happen even it the jdbcTypeForNull is set to JdbcType.NULL during SqlSessionFactory creation. I have noticed that the performance severly degrades when trying to insert an Integer field, that may be null, into a NUMBER column. See the DefaultTimerService in this project for timed examples and the difference in timing between when jdbcType is provided and when it is not in the xml.

## Performance Degradation Location
Most of the processing seems to take place in the ojdbc library when running `OraclePreparedStatement#executeLargeBatch`. I believe the ojdbc library is trying to determine what jdbcType to configure at this time. Though I may be misunderstanding what the decompiled code is doing. 

## (Ideal) Expectation
Match performance when jdbcType is provided in xml sql. 
My assumption is that NULL should be set properly, maybe earlier on due to the fact that jdbcTypeForNull is configured to JdbcType.NULL.

## Known facts
In the [Mapper XML Files -> Parameters](https://mybatis.org/mybatis-3/sqlmap-xml.html#Parameters) documentation it states:
>NOTE The JDBC Type is required by JDBC for all nullable columns, if null is passed as a value. You can investigate this yourself by reading the JavaDocs for the PreparedStatement.setNull() method. 

## Possible Conclusions
1. This may be the reason for the note provided in [Mapper XML Files -> Parameters](https://mybatis.org/mybatis-3/sqlmap-xml.html#Parameters). 
2. This is an issue with MyBatis
3. This is an issue with the ojdbc library
    - It may be important to note that this issue does not seem to exist with Postgres jdbc java library. The performance appears to be the same no matter if the jdbcType is provided in the xml or not.
