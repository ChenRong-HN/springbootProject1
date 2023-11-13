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

public class RescartBizImplTest {

    @Autowired
    RescartBiz rescartBiz;
    @Test
    public void findByName() {
        System.out.println(rescartBiz.findByName("ls"));
    }

    @Test
    void addCart() {
        System.out.println(rescartBiz.check("zs","蛋炒饭"));
    }

    @Test
    void check() {
        System.out.println(rescartBiz.check("zs","酸豆角"));
    }

    @Test
    void updateNum() {
    }
}