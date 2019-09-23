package cn.com.data.engine.api;

import java.util.List;
import java.util.Map;

import cn.com.data.engine.entity.SearchDefineWithBLOBs;
import cn.com.data.engine.entity.SearchExecute;
import cn.com.data.engine.service.BuildSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/engine")
public class BuildSqlApi {
	@Autowired
	BuildSqlService buildSqlService;
	
	@PostMapping(value = "/buildsql")
	public String buildSql(@RequestBody Map<String,Object> params) {
		JSONObject json = new JSONObject(params);
		return buildSqlService.buildSql(json);
	}
	
	@GetMapping(value = "/getDefineList")
	public List<Map> getQueryDefineList() {
		return buildSqlService.getQueryDefineList();
	}
	
	@PostMapping(value = "/addDefine")
	public JSONObject addDefine(@RequestBody SearchDefineWithBLOBs searchdefine) {
		return buildSqlService.addDefine(searchdefine);
	}
	
	@PostMapping(value = "/updateDefine")
	public JSONObject updateDefine(@RequestBody SearchDefineWithBLOBs searchdefine) {
		return buildSqlService.addDefine(searchdefine);
	}
	
	@GetMapping(value = "/deleteDefine")
	public JSONObject deleteDefine(Integer id) {
		return buildSqlService.deleteDefine(id);
	}
	
	
	@GetMapping(value = "/getExecuteList")
	public List<Map> getQueryExecuteList() {
		return buildSqlService.getQueryExecuteList();
	}
	
	@GetMapping(value = "/getExecuteById")
	public SearchExecute getExecuteById(Integer id) {
		return buildSqlService.getExecuteById(id);
	}
	
	
	@GetMapping(value = "/getParams")
	public String getParams(Integer id) {
		return buildSqlService.getParams(id);
	}
	
	@GetMapping(value = "/getVuestate")
	public String getVuestate(Integer id) {
		return buildSqlService.getVuestate(id);
	}
	
	
	
	@PostMapping(value = "/addExecute")
	public JSONObject addExecute(@RequestBody SearchExecute searchexecute) {
		return buildSqlService.addExecute(searchexecute);
	}
	
	@PostMapping(value = "/updateExecute")
	public JSONObject updateExecute(@RequestBody SearchExecute searchexecute) {
		return buildSqlService.updateExecute(searchexecute);
	}
	
	
	@GetMapping(value = "/deleteExecute")
	public JSONObject deleteExecute(Integer id) {
		return buildSqlService.deleteExecute(id);
	}
	
	
	@PostMapping(value = "/buildApi")
	public String buildApi(@RequestBody Map<String,Object> params) {
		return buildSqlService.buildApi(params);
	}
	
}
