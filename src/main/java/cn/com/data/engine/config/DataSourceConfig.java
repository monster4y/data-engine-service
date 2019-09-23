package cn.com.data.engine.config;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.session.LocalCacheScope;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
@MapperScan(basePackages = "cn.com.cnfic.engine.dao")
@Component
@EnableTransactionManagement
// @PropertySource(value = "classpath:application-db.properties")
public class DataSourceConfig {
	private final static Integer SLOW_QUERY_TIME_OUT = 500;
	/**
	 * @Fields jdbcurl : 数据库连接地址
	 */
	@Value("${jdbc.url}")
	private String jdbcurl;
	/**
	 * @Fields dbuser : 数据库登录用户
	 */
	@Value("${dbuser}")
	private String dbuser;
	/**
	 * @Fields dbpass :数据库登录密码
	 */
	@Value("${dbpass}")
	private String dbpass;
	/**
	 * @Fields drivername : 链接驱动名称
	 */
	@Value("${drivername}")
	private String drivername;

	/**
	 * @Title: dataSource @Description: 数据源 @param @return 参数说明 @return DataSource
	 *         返回类型 @throws
	 */

	@Bean
	public ServletRegistrationBean<StatViewServlet> druidServlet() {
		ServletRegistrationBean<StatViewServlet> reg = new ServletRegistrationBean<StatViewServlet>();
		reg.setServlet(new StatViewServlet());
		reg.addUrlMappings("/druid/*");
		reg.addInitParameter("loginUsername", "cnlaw");
		reg.addInitParameter("loginPassword", "adminroot");
		reg.addInitParameter("logSlowSql", "true");
		return reg;
	}

	@Bean
	public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
		FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>();
		filterRegistrationBean.setFilter(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		filterRegistrationBean.addInitParameter("profileEnable", "true");
		return filterRegistrationBean;
	}

	@Bean(value = "dataSource", destroyMethod = "close", initMethod = "init")
	DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setInitialSize(0);
		dataSource.setMinIdle(5);
		dataSource.setMaxActive(50);
		dataSource.setMaxWait(9000);
		dataSource.setTimeBetweenConnectErrorMillis(60000);
		dataSource.setMinEvictableIdleTimeMillis(300000);
		dataSource.setValidationQuery("SELECT 'x'");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setRemoveAbandoned(true);
		dataSource.setRemoveAbandonedTimeoutMillis(7200000);
		dataSource.setLogAbandoned(true);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxOpenPreparedStatements(20);
		dataSource.removeAbandoned();
		ArrayList<Filter> filters = new ArrayList<>();
		filters.add(getSlowSql());
		dataSource.setProxyFilters(filters);
		dataSource.setUrl(jdbcurl);
		dataSource.setDriverClassName(drivername);
		dataSource.setUsername(dbuser);
		dataSource.setPassword(dbpass);
		// dataSource.set
		try {
			dataSource.setFilters("stat");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataSource;
	}

	/**
	 * @Title: sqlSessionFactoryBean @Description:
	 *         sqlsessionfactory @param @return @param @throws IOException
	 *         参数说明 @return SqlSessionFactoryBean 返回类型 @throws
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setConfiguration(getConfiguration());
		sqlSessionFactoryBean.setDataSource(dataSource());
		sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath:/mapping/*.xml"));
		sqlSessionFactoryBean.setTypeAliasesPackage("cn.com.cnfic.engine.entity");
		return sqlSessionFactoryBean;
	}

	/**
	 * @Title: dataSourceTransactionManager @Description:
	 *         transactionmanager @param @return 参数说明 @return
	 *         DataSourceTransactionManager 返回类型 @throws
	 */
	@Bean(name = "transactionManager")
	public DataSourceTransactionManager dataSourceTransactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource());
		return dataSourceTransactionManager;
	}

	/**
	 * @Title: getConfiguration @Description: mybatis configureation @param @return
	 *         参数说明 @return org.apache.ibatis.session.Configuration 返回类型 @throws
	 */
	@Bean
	org.apache.ibatis.session.Configuration getConfiguration() {
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		configuration.setAggressiveLazyLoading(true);
		configuration.setCacheEnabled(true);
		configuration.setLocalCacheScope(LocalCacheScope.SESSION);
		// logback 的实现还没有，用stdout可以打印语句，否则不行
		configuration.setLogImpl(Slf4jImpl.class);
		return configuration;
	}

	// @Bean("DataSourceTransactionManager")
	// public DataSourceTransactionManager transactionManager() {
	// return new DataSourceTransactionManager(dataSource());
	// }
	// @Bean
	// MapperScannerConfigurer getMapperScannerConfigurer() {
	// MapperScannerConfigurer mapperscenconfig = new MapperScannerConfigurer();
	// mapperscenconfig.setBasePackage("com.cbs.web.dao");
	// // mapperscenconfig.setSqlSessionFactoryBeanName("SqlSessionFactoryBean");
	// return mapperscenconfig;
	// }
	@Bean
	com.alibaba.druid.filter.stat.StatFilter getSlowSql() {
		StatFilter filter = new StatFilter();
		filter.setMergeSql(true);
		filter.setSlowSqlMillis(SLOW_QUERY_TIME_OUT);
		filter.setLogSlowSql(true);
		return filter;
	}
}
