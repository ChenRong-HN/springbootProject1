package com.yc.biz;

import com.yc.ResApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ResApp.class})
@Slf4j
public class ResuserBizImplTest {
    @Autowired
    private ResuserBiz resuserBiz;
    @Test
    public void findByName() {
        Assert.notNull(resuserBiz.findByName("a"));
    }

    @Test
    public void testFindByName() {
        log.info(resuserBiz.findByName("zs","a").toString());
    }

    @Test
    public void findById() {
        Assert.notNull(resuserBiz.findById(1));
    }

    @Test
    public void Md5Test(){
        System.out.println(DigestUtils.md5DigestAsHex("b".getBytes()));
    }
}