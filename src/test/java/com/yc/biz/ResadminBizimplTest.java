package com.yc.biz;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class ResadminBizimplTest {

    @Autowired
    ResadminBiz resadminBiz;
    @Test
    public void login() {
        System.out.println(resadminBiz.login("a","a"));
    }
}