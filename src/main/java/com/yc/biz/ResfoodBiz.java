package com.yc.biz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;

import java.util.List;

public interface ResfoodBiz {
    // 查询所有的菜品
    public List<Resfood> findAll();
    // mybatis-plus 自带的分页组件
    public Page<Resfood> findByPage(int pageno,int pagesize,String sortby,String sort);
//    List<Resfood> findByPage(int pageno,int pagesize,String sortby,String sort);
    // 上架新菜品
    public Integer addResfood(Resfood resfood);

    public Resfood findById(Integer fid);
}
