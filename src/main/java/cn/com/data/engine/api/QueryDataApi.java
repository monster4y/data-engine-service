package cn.com.data.engine.api;

import java.util.Map;
import java.util.concurrent.Callable;

import cn.com.data.engine.service.ExecuteApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/engine")
public class QueryDataApi {

	@Autowired
    ExecuteApiService executeApiService;

	@GetMapping(value = "/dataApi/{apipath}")
	public Callable<String> dataApi(@PathVariable String apipath, @RequestParam Map<String, Object> params) {
		Callable<String> result = () -> {
			return executeApiService.executeApi(apipath, params);
		};
		return result;
	}

	@PostMapping(value = "/dataApi/{apipath}")
	public Callable<String> dataApiPost(@PathVariable String apipath, @RequestBody Map<String, Object> params) {
		Callable<String> result = () -> {
			return executeApiService.executeApi(apipath, params);
		};
		return result;
	}

	@PostMapping(value = "/testApi/{defineid}")
	public Callable<String> testApi(@PathVariable Integer defineid, @RequestBody Map<String, Object> params) {
		Callable<String> result = () -> {
			return executeApiService.testApi(defineid, params);
		};
		return result;
	}

}
