package cn.com.data.engine.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import cn.com.data.engine.cache.DruidDSCache;
import cn.com.data.engine.dao.DataSourceMapper;
import cn.com.data.engine.entity.DataSourceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.google.common.collect.Maps;

@Component
public class DbManager {

	@Autowired
	private DataSourceMapper dataSourceMapper;

	public Connection getConnection(DataSourceInfo dataSource) {
		return getConnection(dataSource.getId().toString());
	}

	public Connection getConnection(String dsId) {
		DataSource ds = getDS(dsId);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

//	public static void releaseConnection(Connection conn, DataSourceInfo dataSource) {
//		DataSourceUtils.releaseConnection(conn, covertDataSource(dataSource));
//	}
//
//	public static boolean testConnect(DataSourceInfo dataSource) {
//		return getConnection(dataSource) == null;
//	}

//	private static DriverManagerDataSource covertDataSource(DataSourceInfo dataSource) {
//		DriverManagerDataSource dataSource1 = new DriverManagerDataSource();
//		dataSource1.setDriverClassName(dataSource.getDriverClass());
//		dataSource1.setUrl(dataSource.getUrl());
//		dataSource1.setUsername(dataSource.getDbUser());
//		dataSource1.setPassword(dataSource.getDbPassword());
//		return dataSource1;
//	}

	public DataSource getDS(String id) {
		DataSource dsResult = null;
		if (DruidDSCache.get(id) != null) {
			dsResult = DruidDSCache.get(id);
		} else {
			dsResult = makeDruidDS(id);
			DruidDSCache.put(id, dsResult);
		}
		return dsResult;
	}

	private DataSource makeDruidDS(String id) {
		DataSource datasource = null;
		try {
			DataSourceInfo dsInfo = dataSourceMapper.selectByPrimaryKey(Integer.parseInt(id));
			Map<String, Object> map = Maps.newHashMap();
			map.put("jdbcUrl", dsInfo.getUrl());
			map.put("url", dsInfo.getUrl());
			map.put("username", dsInfo.getDbUser());
			map.put("password", dsInfo.getDbPassword());
//			map.put(key, value);
			datasource = DruidDataSourceFactory.createDataSource(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datasource;
	}

}
