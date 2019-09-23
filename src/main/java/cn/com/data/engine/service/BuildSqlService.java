package cn.com.data.engine.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.com.data.engine.entity.SearchDefineWithBLOBs;
import cn.com.data.engine.entity.SearchExecute;

public interface BuildSqlService {

	public String buildSql(JSONObject json);
	
	public String buildApi(Map params);

	public List<Map> getQueryDefineList();

	public JSONObject addDefine(SearchDefineWithBLOBs searchdefine);
	
	public JSONObject updateDefine(SearchDefineWithBLOBs searchdefine);
	
	public JSONObject deleteDefine(Integer id);
	
	public List<Map> getQueryExecuteList();
	
	public SearchExecute getExecuteById(Integer id);
	
	public JSONObject addExecute(SearchExecute searchexecute);
	
	public JSONObject updateExecute(SearchExecute searchexecute);
	
	public JSONObject deleteExecute(Integer id);
	
	public String getParams(Integer id);
	
	public String getVuestate(Integer id);
	
}
