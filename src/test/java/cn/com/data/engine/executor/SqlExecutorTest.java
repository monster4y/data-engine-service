package cn.com.data.engine.executor;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.com.data.engine.sql.executor.SqlExecutor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlExecutorTest {

    @Autowired SqlExecutor sqlExecutor;
    
    @Test
    public void executeSqlTest() {
    	List list = sqlExecutor.executeSql("select id from sys_role where name = ? and code = ?", "5",new Object[] {"普通用户","normal"});
    	System.out.println("HHHH::::"+list.size());
    }
    


}
