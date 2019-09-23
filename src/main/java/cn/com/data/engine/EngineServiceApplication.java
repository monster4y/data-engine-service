package cn.com.data.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
@EnableCaching
@SpringBootApplication
public class EngineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EngineServiceApplication.class, args);
	}

}

