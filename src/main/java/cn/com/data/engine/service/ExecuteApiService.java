package cn.com.data.engine.service;

import java.util.Map;

public interface ExecuteApiService {
	
	public String executeApi(String path,Map<String,Object> params);
	
	public String testApi(Integer id,Map<String,Object> params);
	
}
