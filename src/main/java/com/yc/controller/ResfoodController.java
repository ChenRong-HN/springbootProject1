package com.yc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBiz;
import com.yc.config.RedisKeys;
import com.yc.web.model.MyPageBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resfood")
@Slf4j
//@Api(tags = "菜品管理")
public class ResfoodController {
    @Autowired
    private ResfoodBiz resfoodBiz;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "detailCountAdd",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "查看详情次数增加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid" , value = "菜品号" , required = true)
    })
    public Map<String,Object> detailCountAdd(@RequestParam Integer fid) {
        Map<String,Object> map = new HashMap<>();
        Long count = 1L; // 使该键自增
        if (redisTemplate.hasKey(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + fid) == false){
            redisTemplate.opsForValue().set(RedisKeys.RESFOOD_DETAIL_COUNT_FID_+fid,count);
        }else {
            count = redisTemplate.opsForValue().increment(RedisKeys.RESFOOD_DETAIL_COUNT_FID_+fid);
        }
        map.put("code",1);
        map.put("obj",count); // obj 存正常返回的结果


        return map;
    }

    @RequestMapping(value = "/findById/{fid}",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "根据菜品编号查询操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid" , value = "菜品号" , required = true)
    })
    public Map<String,Object> findById(@PathVariable Integer fid){
        Map<String,Object> map = new HashMap<>();
        Resfood resfood = null;
        try {
            resfood = this.resfoodBiz.findById(fid);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",0);  // code 表示是否正常
            map.put("msg",e.getCause());
            return map;
        }
        map.put("code",1);
        map.put("obj",resfood);  //obj存取正常返回的运算结果
        return map;
    }
    @RequestMapping(value = "/findByPage",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> findByPage(@RequestParam int pageno,@RequestParam int pagesize,
                                         @RequestParam(required = false) String sortby,@RequestParam(required = false) String sort){
        Map<String,Object> map = new HashMap<>();
        // page是dao层组件，这种称为PO对象（持久化对象）-> 与表结构相同，到controller层要转化成VO对象
        Page<Resfood> page = null;
        try{
            page = this.resfoodBiz.findByPage(pageno,pagesize,sortby,sort);
        } catch (Exception e){
            map.put("code",0);
            map.put("msg",e.getCause());
            e.printStackTrace();
            return map;
        }

        map.put("code",1);
        // 创建一个web model ，用于在web/app界面上显示结果
        MyPageBean pageBeanVO = new MyPageBean();
        pageBeanVO.setPageno(pageno);
        pageBeanVO.setPagesize(pagesize);
        pageBeanVO.setSort(sort);
        pageBeanVO.setSortBy(sortby);
        pageBeanVO.setTotal(page.getTotal()); //总记录数
        pageBeanVO.setDataset(page.getRecords()); //记录集合
        //其他分页数据
        //计算总页数
        long totalPages = page.getTotal() % pageBeanVO.getPagesize() == 0 ?
                page.getTotal()/pageBeanVO.getPagesize() : page.getTotal() / pageBeanVO.getPagesize() + 1;
        pageBeanVO.setTotalpages( (int)totalPages);
        // 上一页号的计算
        if (pageBeanVO.getPageno() <= 1){
            pageBeanVO.setPre(1);
        } else {
            pageBeanVO.setPre(pageBeanVO.getPageno() - 1);
        }
        //下一页的计算
        if (pageBeanVO.getPageno() == totalPages){
            pageBeanVO.setNext((int)totalPages);
        } else {
            pageBeanVO.setNext(pageBeanVO.getPageno() + 1);
        }
        map.put("data",pageBeanVO);
        return map;
    }

    @RequestMapping(value = "/findAll",method = {RequestMethod.GET,RequestMethod.POST})
    public List<Resfood> findAll(){
        try {

        } catch (Exception e){
            Map<String,Object> map = new HashMap<>();
            map.put("code",0);
            map.put("msg",e.getCause());
            e.printStackTrace();
            return (List<Resfood>) map;
        }
        return this.resfoodBiz.findAll();
    }




}
