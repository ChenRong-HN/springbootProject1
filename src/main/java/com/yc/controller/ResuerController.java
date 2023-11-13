package com.yc.controller;

import com.yc.StringUtils;
import com.yc.bean.CartItem;
import com.yc.bean.Rescart;
import com.yc.bean.Resuser;
import com.yc.biz.ResuserBiz;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/resuser")
@Slf4j
@Api(tags = "用户操作")
public class ResuerController {
    @Autowired
    private ResuserBiz resuserBiz;

    @RequestMapping(value = "/findByName",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> findByName(@RequestParam String username, @RequestParam String password, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        Resuser resuser = null;
        try{
            resuser = resuserBiz.findByName(username,password);
            if (resuser == null){
                map.put("code",0);
                map.put("msg","用户名或密码错误！");
                return map;
            }
        }catch (RuntimeException e){
            map.put("msg",e.getCause());
            map.put("code",0);
            e.printStackTrace();
            return map;
        }
        session.setAttribute("resuser",resuser);
        map.put("data",resuser);
        map.put("code",1);
        return map;

    }

    ////////////////////////
    @RequestMapping(value = "/logout",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> logout(HttpSession session){
        Map<String,Object> map = new HashMap<>();
        session.removeAttribute("resuser");
        session.removeAttribute("cart");
        map.put("code",1);
        return map;
    }

    @RequestMapping(value = "/isLogin",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> isLogin(HttpSession session){
        Map<String,Object> map = new HashMap<>();
        if (session.getAttribute("resuser") != null){
            Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
            map.put("code",1);
            map.put("data",session.getAttribute("resuser"));
            map.put("cart",cart);
            System.out.println("=============================");
            System.out.println(cart);
        }else {
            Resuser resuser = (Resuser) session.getAttribute("resuser");
            map.put("code",0);
            map.put("data",resuser);
        }
        return map;
    }

    @RequestMapping(value = "/login",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> login(Resuser resuser, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        // 取出验证码
        String code = (String)session.getAttribute("code");
        if(!resuser.getYzm().equals(code)){
            map.put("code",-1);
            map.put("msg","验证吗错误！");
            return map;
        }
        if(StringUtils.isEmpty(resuser.getUsername()) || StringUtils.isEmpty(resuser.getPwd())){
            map.put("code",-2);
            map.put("msg","用户名或密码为空！");
            return map;
        }
        //处理密码: 用md5 加密
        String md5 = DigestUtils.md5DigestAsHex(resuser.getPwd().getBytes());

        //访问业务层
        Resuser ru = resuserBiz.findByName(resuser.getUsername(),md5);
        if(ru == null){
            //失败，则code=-3
            map.put("code",-3);
            map.put("msg","用户名或密码错误");
            return map;
        }
        //成功， 则code=1
        map.put("code",1);
        //回送一个数据给客户端
        ru.setPwd(""); // 密码置空
        // 在session 中保存用户信息，以维持用户登录状态
        session.setAttribute("resuser",ru);
        map.put("obj",ru);
        return map;

    }

}
