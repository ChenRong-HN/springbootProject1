package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.Resfood;
import lombok.Data;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface ResfoodMapper extends BaseMapper<Resfood> {
//    @Select("select * form resfood order by #{sortby} #{sort} limit ")
//    List<Resfood> findByPage(int pageno,int pagesize,String sortby,String sort);
}
// 利用动态代理根据这个接口的方法来生成一个代理对象，并将所有的方法重写
