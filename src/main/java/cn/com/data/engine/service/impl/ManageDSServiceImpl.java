package cn.com.data.engine.service.impl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.com.data.engine.service.ManageDSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.com.data.engine.cache.MeteDataCache;
import cn.com.data.engine.dao.DataSourceMapper;
import cn.com.data.engine.db.DbManager;
import cn.com.data.engine.entity.DataSourceInfo;
import cn.com.data.engine.entity.DbFieldInfo;
import cn.com.data.engine.entity.DbTableInfo;

@Transactional
@Service("DataSourceService")
public class ManageDSServiceImpl implements ManageDSService {

	@Autowired
	DataSourceMapper dataSourceMapper;
	
	@Autowired
	DbManager dbHelper;
	
	@Autowired
	MeteDataCache meteDataCache;

	@Override
	public List<DbTableInfo> getTableNameList(Integer sourceId) {
		ResultSet resultSet = null;
		List<DbTableInfo> dbTableInfos = new ArrayList<DbTableInfo>();
//		 DataSourceInfo datasourceInfo = dataSourceMapper.selectByPrimaryKey(sourceId);
//		Connection connection = dbHelper.getConnection(datasourceInfo);
		DatabaseMetaData metaData = meteDataCache.get(String.valueOf(sourceId));
//		sqlSessionTemplate.select("", null);
		try {
			String[] types = { "TABLE" };
			String driverName = metaData.getDriverName().toUpperCase();
			if (driverName.contains("ORACLE")) {
				resultSet = metaData.getTables(null, "", null, types);
			} else {
				resultSet = metaData.getTables(null, null, null, types);
			}
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				String remarks = resultSet.getString("REMARKS");
				DbTableInfo dbTableInfo = new DbTableInfo();
				dbTableInfo.setTableName(tableName);
				dbTableInfo.setRemarks(remarks);
				dbTableInfos.add(dbTableInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
//			DbHelper.releaseConnection(connection, datasourceInfo);
		}
		return dbTableInfos;
	}
	
	
	@Override
	public List getFieldListByTbId(Integer sourceid, String tbname) {
//		ResultSet resultSet = null;
//		 DataSourceInfo datasourceInfo = dataSourceMapper.selectByPrimaryKey(sourceid);
//		Connection connection = dbHelper.getConnection(datasourceInfo);
		List result=new ArrayList();
		DatabaseMetaData metaData = meteDataCache.get(String.valueOf(sourceid));
//		Map<String,Object> columnNameMap=new HashMap<>();
		try {
//			String driverName = metaData.getDriverName().toUpperCase();
			ResultSet columns =  metaData.getColumns(null, null, tbname, "%");
	        while (columns.next()) {
	            String column_name = columns.getString("COLUMN_NAME");
	            String type_name = columns.getString("TYPE_NAME");
	            DbFieldInfo dbFieldInfo = new DbFieldInfo();
	            dbFieldInfo.setFieldName(column_name);
	            dbFieldInfo.setFieldType(type_name);
//	            columnNameMap.put(column_name, type_name);
	            result.add(dbFieldInfo);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
//			DbHelper.releaseConnection(connection, datasourceInfo);
		}
		return result;
		
	}

	@Override
	public JSONObject addDataSource(DataSourceInfo datasource) {
		JSONObject result = new JSONObject();
		dataSourceMapper.insertSelective(datasource);
		result.put("status", "ok");
		result.put("msg", "添加成功");
		return result;
	}

	@Override
	public JSONObject removeDataSource(Integer id) {
		JSONObject result = new JSONObject();
		dataSourceMapper.deleteByPrimaryKey(id);
		result.put("status", "ok");
		return result;
	}

	@Override
	public List<Map> getDsList() {
//		dataSourceMapper.fetchDsList();
		return dataSourceMapper.fetchDsList();
	}


	@Override
	public JSONObject updateDataSource(DataSourceInfo datasource) {
		JSONObject result = new JSONObject();
		dataSourceMapper.updateByPrimaryKeySelective(datasource);
		result.put("status", "ok");
		result.put("msg", "添加成功");
		return result;
	}

	
}
