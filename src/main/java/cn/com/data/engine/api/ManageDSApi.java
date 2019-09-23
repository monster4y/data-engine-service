package cn.com.data.engine.api;

import java.util.List;
import java.util.Map;

import cn.com.data.engine.entity.DataSourceInfo;
import cn.com.data.engine.service.ManageDSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/engine")
public class ManageDSApi {
	
	@Autowired
    ManageDSService manageDSService;
	
	@GetMapping(value = "/getDsList")
	public List<Map> getDataSourceList() {
		return manageDSService.getDsList();
	}
	
	@GetMapping(value = "/getTBInfoListByDsId")
	public List getTBInfoListByDsId(Integer sourceid) {
		return manageDSService.getTableNameList(sourceid);
	}
	
	@GetMapping(value = "/getFBInfoListByTbId")
	public List getFBInfoListByTbId(Integer sourceid,String tbname) {
		return manageDSService.getFieldListByTbId(sourceid, tbname);
	}
	
	@PostMapping(value = "/addDs")
	public JSONObject addDataSource(@RequestBody DataSourceInfo datasource) {
		manageDSService.addDataSource(datasource);
		return manageDSService.addDataSource(datasource);
	}
	
	@PostMapping(value = "/updateDs")
	public JSONObject updateDataSource(@RequestBody DataSourceInfo datasource) {
		return manageDSService.updateDataSource(datasource);
	}
	
	
	@GetMapping(value = "/deleteDs")
	public JSONObject removeDataSource(Integer id) {
		return manageDSService.removeDataSource(id);
	}

}
