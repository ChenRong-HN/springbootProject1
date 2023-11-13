package com.yc.controller;

import com.yc.ResApp;
import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBiz;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ResApp.class},
        //使用随机端口，避免多个测试用例同时启动时不会发生端口占用问题
        webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
@AutoConfigureMockMvc(addFilters = false)
public class ResfoodControllerTest {

    @Autowired
    //模拟请求发送
    private MockMvc mockMvc;

    private Resfood resfood;
    @Test
    @DisplayName("通过id查询")
    public void findById() {
        try{
            //MockMvcRequestBuilders：请求构建器
            mockMvc.perform(MockMvcRequestBuilders.get("/resfood/findById")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .param("fid","1")
            )
                    // andExpect：相当于java中的断言  MockMvcResultMatchers: 结果匹配器
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    // 用于返回body
                    .andExpect(MockMvcResultMatchers.jsonPath("$.fname", Matchers.equalToIgnoringCase(resfood.getFname())))
                    //如果符合预期  则进行print输出响应信息
                    .andDo(print())
                    // 真正请求得到的内容，真正执行的返回值
                    .andReturn();
        }catch (Exception e){
            log.error("异常");
            throw new RuntimeException(e);
        }
    }

    @Autowired
    ResfoodBiz resfoodBiz;
    @Test
    public List<Resfood> findAll(){
        System.out.println(this.resfoodBiz.findAll());
        return this.resfoodBiz.findAll();
    }
}