package cn.com.data.engine.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

public class DruidDSCache {
	
	private static Map<String,DataSource> dsMap=new ConcurrentHashMap<>();
	
	
	public static DataSource get(String key) {
		return dsMap.get(key);
	}
	
	public static void put(String key,DataSource ds) {
		dsMap.put(key, ds);
	}
	
	
}
