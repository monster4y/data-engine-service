package cn.com.data.engine.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.com.data.engine.entity.DataSourceInfo;
import cn.com.data.engine.entity.DbTableInfo;

public interface ManageDSService {

    public List<DbTableInfo> getTableNameList(Integer soureid);

    public JSONObject addDataSource(DataSourceInfo datasource);
    
    public JSONObject updateDataSource(DataSourceInfo datasource);
    
    public JSONObject removeDataSource(Integer id);
    
    public List<Map> getDsList();
    
    public List<Map<String,Object>> getFieldListByTbId(Integer sourceid,String tbname);
    
}
