package com.yc.biz;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Rescart;
import com.yc.dao.RescartMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional(readOnly = false)
public class RescartBizImpl implements RescartBiz{

    @Autowired
    private RescartMapper rescartMapper;
    @Override
    public List<Rescart> findByName(String username) {
        QueryWrapper wrapper  = new QueryWrapper();
        wrapper.select("id","username","fname","num","price");
        wrapper.eq("username",username);
        return rescartMapper.selectList(wrapper);
    }

    @Override
    public int addCart(Rescart rescart) {
        return rescartMapper.insert(rescart);
    }

    @Override
    public int check(String username, String fname) {
        return rescartMapper.check(username, fname);
    }

    @Override
    public int updateNum(String username, String fname, int num) {
        return rescartMapper.updateNum(username, fname, num);
    }


}
