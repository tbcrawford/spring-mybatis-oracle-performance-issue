package io.perf.test.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = "io.perf.test.dao", sqlSessionTemplateRef = "oraSqlSessionTemplate")
public class OracleConfig {

	private static final String DB_USERNAME_SYSTEM_VAR_NAME = "ora.username";
	private static final String DB_PASSWORD_SYSTEM_VAR_NAME = "ora.password";
	private static final String DB_URL_SYSTEM_VAR_NAME = "ora.url";

	@Bean
	public DataSource oraDataSource() throws Exception {
		return DataSourceBuilder.create()
				.username(System.getProperty(DB_USERNAME_SYSTEM_VAR_NAME))
				.password(System.getProperty(DB_PASSWORD_SYSTEM_VAR_NAME))
				.url(System.getProperty(DB_URL_SYSTEM_VAR_NAME))
				.driverClassName("oracle.jdbc.OracleDriver")
				.build();

	}

	@Bean
	public SqlSessionFactory oraSqlSessionFactory(DataSource oraDataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(oraDataSource);
		factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:dao/*.xml"));

		SqlSessionFactory factory = factoryBean.getObject();
		factory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);

		// NOTE: When doing Sring Transactions, this is incredibly important to set!
		//  This could make the transaction 100x faster (that's a literal number, not a figure of speech)
		factory.getConfiguration().setDefaultExecutorType(ExecutorType.BATCH);

		return factory;
	}

	@Bean
	public DataSourceTransactionManager oraTransactionManager(DataSource oraDataSource) {
		return new DataSourceTransactionManager(oraDataSource);
	}

	@Bean
	public SqlSessionTemplate oraSqlSessionTemplate(SqlSessionFactory oraSqlSessionFactory) {
		return new SqlSessionTemplate(oraSqlSessionFactory);
	}

}
