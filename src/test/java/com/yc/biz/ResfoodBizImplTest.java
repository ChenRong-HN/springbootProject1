package com.yc.biz;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ResfoodBizImplTest {

    @Autowired
    ResfoodBiz resfoodBiz;

    @Test
    public void findAll() {
    }

    @Test
    public void findByPage() {
        log.info(resfoodBiz.findByPage(3,3,"fid","asc").toString());
    }

    @Test
    public void addResfood() {
    }

    @Test
    public void findById() {
    }
}