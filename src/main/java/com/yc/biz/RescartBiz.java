package com.yc.biz;

import com.yc.bean.Rescart;

import java.util.List;

public interface RescartBiz {

    public List<Rescart> findByName(String username);
    public int addCart(Rescart rescart);
    int check(String username, String fname);
    int updateNum(String username,String fname,int num);

}
