package cn.com.data.engine.sql.executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqlExecutor {
	
	@Autowired QueryExecutorFactory queryExecutorFactory;
	
	public <T> List<T> executeSql(String sql,Connection conn,Object[] params) {
		
		return null;
	}
	
	
	
	public  <T> List<T> executeSql(String sql,String dataSourceId,Object[] params) {
		List result=null;
		try {
			result = queryExecutorFactory.getQueryRunnerByDataSourceId(dataSourceId).query(sql,new MapListHandler(), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
