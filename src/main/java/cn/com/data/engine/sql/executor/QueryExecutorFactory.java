package cn.com.data.engine.sql.executor;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.data.engine.db.DbManager;

@Component
public class QueryExecutorFactory {
	
	@Autowired 
	private DbManager dbHelper;
	
	public  QueryRunner getQueryRunnerByDataSourceId(String datasourceId) {
		return new QueryRunner(dbHelper.getDS(datasourceId));
	}
	
}
