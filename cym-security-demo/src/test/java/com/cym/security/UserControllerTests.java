
package com.cym.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//指定启动类，如果不指定，加载所有启动类，可能出现
//Found multiple @SpringBootConfiguration annotated
@SpringBootTest(classes = CymSecurityDemoApplication.class)
public class UserControllerTests {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenGetInfoSuccess() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user")
                //添加参数
                .param("name", "张三")
                .param("password", "123456")
                .param("age", "11")
                //数据格式json
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //返回状态是否200
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----------" + result);

    }

    @Test
    public void whenGetInfoSuccess2() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                //数据格式json
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //返回状态是否200
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.age").value(11))
                .andReturn().getResponse().getContentAsString();
        System.out.println("============" + result);
    }

    @Test
    public void whenPostInfoSuccess() throws Exception {
        String content = "{\"age\":10,\"name\":\"张三\",\"password\":\"123456789\"}";
        String result = mockMvc.perform(MockMvcRequestBuilders.post("/user")
                //数据格式json
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                //返回状态是否200
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("============" + result);
    }


}
