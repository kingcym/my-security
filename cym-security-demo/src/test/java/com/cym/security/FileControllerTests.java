
package com.cym.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
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
public class FileControllerTests {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    /**
     * 文件上传
     * @throws Exception
     */
    @Test
    public void whenGetInfoSuccess() throws Exception {
        String result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/file")
                .file(new MockMultipartFile("file","ss.txt","multipart/form-data","hekk".getBytes("UTF-8"))))
                //返回状态是否200
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println("-----------" + result);
    }


}
