
package com.cym.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//指定启动类，如果不指定，加载所有启动类，可能出现
//Found multiple @SpringBootConfiguration annotated
@SpringBootTest(classes=CymSecurityDemoApplication.class)
public class CymSecurityDemoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
