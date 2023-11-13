package com.yc.controller;

import com.yc.bean.CartItem;
import com.yc.bean.Rescart;
import com.yc.bean.Resfood;
import com.yc.biz.RescartBiz;
import com.yc.biz.ResfoodBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/rescart")
@Api(tags = "购物车管理")
public class RescartController {
    @Autowired
    RescartBiz rescartBiz;
    @Autowired
    ResfoodBiz resfoodBiz;
    @RequestMapping(value = "/findByName",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> findByName(@RequestParam String username,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        if (rescartBiz.findByName(username) != null) {
            map.put("data",rescartBiz.findByName(username));
            map.put("code",1);
        }
        session.setAttribute("cart",rescartBiz.findByName(username));
        return map;
    }

    @RequestMapping(value = "/clearALl",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "清空购物车")
    public Map<String,Object> addCart(HttpSession session){
        Map<String,Object> map = new HashMap<>();
        session.removeAttribute("cart");
//        session.invalidate(); // 清除该用户session中的全部数据  不要轻易使用！
        map.put("code",1);
        return map;
    }

    @RequestMapping(value = "/addCart",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> addCart(@RequestParam String username,@RequestParam String fname,
                                      Integer num,@RequestParam Integer price){
        //每次新增数量默认为1
        num = 1;
        Map<String,Object> map = new HashMap<>();
        Rescart rescart = new Rescart(null,username,fname,num,price);
        if (rescartBiz.check(username, fname) == 0){ // 如果购物车中不存在则添加购物车
            rescartBiz.addCart(rescart);
        }else {
            rescartBiz.updateNum(username, fname, num); // 如果存在则修改数量
        }
        try{

        }catch (Exception e){
            map.put("code",0);
            map.put("msg","新增失败"+e.getCause());
            return map;
        }
        map.put("code",1);
        map.put("msg","新增成功！");
        return map;
    }

    @RequestMapping(value = "/check",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> check(@RequestParam String username){
        Map<String,Object> map = new HashMap<>();
        map.put("code",rescartBiz.findByName(username).size());
        return map;
    }

//    @RequestMapping(value = "/addCart",method = {RequestMethod.GET,RequestMethod.POST})
//    @ApiOperation(value = "添加购物车")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "fid",value = "菜品号",required = true),
//            @ApiImplicitParam(name = "num",value = "数量",required = true)
//    })
//    public  Map<String,Object> addCart(@RequestParam Integer fid,
//                                       @RequestParam Integer num, HttpSession session){
//        // 根据fid取商品信息
//        Resfood food = resfoodBiz.findById(fid);
//        if (food == null){
//
//        }
//        Map<Integer, CartItem> cart = new HashMap<>();
//        if (session.getAttribute("cart") != null){
//            cart = (Map<Integer, CartItem>) session.getAttribute("cart");
//        } else {
//            session.setAttribute("cart",cart);
//        }
//        CartItem cartItem;
//        //判断这个商品
//        return null;
//    }


}
