package cn.com.data.engine.service.impl;

import java.util.Map;

import cn.com.data.engine.service.ExecuteApiService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.com.data.engine.dao.SearchDefineMapper;
import cn.com.data.engine.dao.SearchExecuteMapper;
import cn.com.data.engine.entity.SearchDefineWithBLOBs;
import cn.com.data.engine.sql.executor.SqlExecutor;

@Service
public class ExecuteApiServiceImpl implements ExecuteApiService {

	@Autowired
	SearchExecuteMapper searchExecuteMapper;

	@Autowired
	SearchDefineMapper searchDefineMapper;

	@Autowired
	SqlExecutor sqlExecutor;

	@Override
	public String executeApi(String path, Map<String, Object> paramsMap) {
		Map data = searchExecuteMapper.fetchExecuteByUrl("/" + path);
		String params = ObjectUtils.toString(data.get("params"));
		String sql = ObjectUtils.toString(data.get("sqlcontent"));
		String dataSourceId = ObjectUtils.toString(data.get("dsid"));
		sqlExecutor.executeSql(sql, dataSourceId, prepareParams(params, paramsMap));
		return JSON.toJSONString(sqlExecutor.executeSql(sql, dataSourceId, prepareParams(params, paramsMap)));
	}

	private Object[] prepareParams(String params, Map<String, Object> paramsMap) {
		String[] parray = StringUtils.split(params, ",");
		int plen = parray.length;
		Object[] rarray = new Object[plen];
		for (int i = 0; i < plen; i++) {
			rarray[i] = paramsMap.get(parray[i]);
		}
		return rarray;
	}

	@Override
	public String testApi(Integer id, Map<String, Object> paramsMap) {
		SearchDefineWithBLOBs data = searchDefineMapper.selectByPrimaryKey(id);
		String params = data.getParams();
		String sql = data.getSqlcontent();
		Integer dataSourceId = data.getDsid();
//	    sqlExecutor.executeSql(sql, String.valueOf(dataSourceId),prepareParams(params,paramsMap));
		return JSON.toJSONString(
				sqlExecutor.executeSql(sql, String.valueOf(dataSourceId), prepareParams(params, paramsMap)));
	}

}
