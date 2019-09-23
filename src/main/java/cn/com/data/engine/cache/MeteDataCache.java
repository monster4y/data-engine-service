package cn.com.data.engine.cache;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import cn.com.data.engine.entity.DataSourceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import cn.com.data.engine.dao.DataSourceMapper;
import cn.com.data.engine.db.DbManager;

@Component
public class MeteDataCache {

	private Cache<String, DatabaseMetaData> cache = CacheBuilder.newBuilder().maximumSize(100)
			.expireAfterWrite(24, TimeUnit.HOURS).build();

	@Autowired
	DataSourceMapper dataSourceMapper;

	@Autowired
	DbManager dbHelper;

	public DatabaseMetaData get(String sourceId) {
		DatabaseMetaData result = null;
		try {
			result = cache.get("key", new Callable<DatabaseMetaData>() {
				public DatabaseMetaData call() throws SQLException {
					DataSourceInfo datasourceInfo = dataSourceMapper.selectByPrimaryKey(Integer.parseInt(sourceId));
					Connection connection = dbHelper.getConnection(datasourceInfo);
					DatabaseMetaData metaData = connection.getMetaData();
//					connection.close();
					return metaData;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
