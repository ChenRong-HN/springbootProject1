package com.yc.dao;

import com.yc.ResApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ResApp.class})
@Slf4j
public class TestDb {
    @Autowired
    private DataSource dataSource;
    @Test
    public void getConnection() throws SQLException {
        log.info(dataSource.getConnection().toString());
    }
    @Autowired
    private ResfoodMapper resfoodMapper;
    @Test
    public void ResfoodMapperTest(){
        log.info(resfoodMapper.selectList(null).toString());
    }
}
