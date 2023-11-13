package com.yc.controller;

import com.yc.bean.CartItem;
import com.yc.bean.Resfood;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.biz.ResfoodBiz;
import com.yc.biz.ResorderBiz;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/resorder")
@Api(tags = "购物车管理")
public class ResorderController {

    @Autowired
    private ResfoodBiz resfoodBiz;
    @Autowired
    private ResorderBiz resorderBiz;

    @RequestMapping(value = "getCartInfo",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "获取购物车列表")
    public Map<String,Object> getCartInfo(HttpSession session){
        Map<String,Object> map = new HashMap<>();
        if (session.getAttribute("cart") == null || ((Map<Integer,CartItem>)session.getAttribute("cart")).size() <= 0){
            map.put("code",0);
            return map;
        }
        Map<Integer,CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        map.put("code",1);
        map.put("obj",cart.values()); //返回的是map 值是set
        return map;
    }

    @RequestMapping(value = "confirmOrder",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "提交订单")
    public Map<String,Object> confirmOrder(Resorder order, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        if (session.getAttribute("cart") == null || ((Map<String, Object>)session.getAttribute("cart")).size() == 0){
            map.put("code",-1);
            map.put("msg","暂无任何商品");
            return map;
        }
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (session.getAttribute("resuser") == null){
            map.put("code",-2);
            map.put("msg","非登录用户不能下单");
            return map;
        }
        Resuser resuser = (Resuser) session.getAttribute("resuser");
        order.setUserid(resuser.getUserid());
        //orderTime 下单时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        order.setOrdertime(formatter.format(now));
        if(order.getDeliverytime() == null || "".equals(order.getDeliverytime())){
            LocalDateTime deliveryTime = now.plusHours(1);
            order.setDeliverytime(formatter.format(deliveryTime));
        }
        order.setStatus(0);
        try{
            resorderBiz.order(order, new HashSet(cart.values()),resuser);
        }catch (Exception e){
            map.put("code",-3);
            map.put("msg",e.getMessage());
            e.printStackTrace();
            return map;
        }
        map.put("code",1);
        return map;
    }

    @RequestMapping(value = "addCart",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "添加购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid",value = "菜品号",required = true),
            @ApiImplicitParam(name = "num",value = "数量",required = true)
    })
    public Map<String,Object> addCart(@RequestParam Integer fid,@RequestParam Integer num, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        //根据id取出商品信息
        Resfood food = resfoodBiz.findById(fid);
        if(food == null){
            map.put("code",-1);
            map.put("msg","查无此商品");
            return  map;
        }
        //从session中取出Cart
        Map<Integer,CartItem> cart = new HashMap<>();
        if (session.getAttribute("cart") != null){
            cart = ((Map<Integer, CartItem>) session.getAttribute("cart"));
        }else{
            session.setAttribute("cart",cart);
        }
        //判断改商品是否在map中  有 修改数量  无  创建一个CartItem，存入map中
        CartItem ci;
        if (cart.containsKey(fid)){
             ci = cart.get(fid);
            ci.setNum(ci.getNum()+num);
            cart.put(fid,ci);
        }else {
            ci = new CartItem();
            ci.setNum(num);
            ci.setFood(food);
            cart.put(fid,ci);
        }
        //处理数量
        if (ci.getNum() <= 0){
            cart.remove(fid);
        }

        session.setAttribute("cart",cart);
        map.put("code",1);
        map.put("obj",cart.values());
        return map;
    }

    @RequestMapping(value = "/clearALl",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value = "清空购物车")
    public Map<String,Object> clearAll(HttpSession session){
        Map<String,Object> map = new HashMap<>();
        session.removeAttribute("cart");
//        session.invalidate(); // 清除该用户session中的全部数据  不要轻易使用！
        map.put("code",1);
        return map;
    }


}
