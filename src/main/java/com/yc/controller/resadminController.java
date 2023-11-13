package com.yc.controller;

import com.yc.StringUtils;
import com.yc.bean.Resadmin;
import com.yc.bean.Resuser;
import com.yc.biz.ResadminBiz;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/resadmin")
@Api(tags = "后台登录")
public class resadminController {
    @Autowired
    private ResadminBiz resadminBiz;

    @RequestMapping(value = "/login",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> login(@RequestParam String raname,@RequestParam String rapwd, HttpSession session){
        Map<String,Object> map = new HashMap<>();

        if(StringUtils.isEmpty(raname) || StringUtils.isEmpty(rapwd)){
            map.put("code",-2);
            map.put("msg","用户名或密码为空！");
            return map;
        }
        //处理密码: 用md5 加密
        String md5 = DigestUtils.md5DigestAsHex(rapwd.getBytes());

        //访问业务层
        Resadmin ra = resadminBiz.login(raname,md5);
        if(ra == null){
            //失败，则code=-3
            map.put("code",-3);
            map.put("msg","用户名或密码错误");
            return map;
        }
        //成功， 则code=1
        map.put("code",1);
        //回送一个数据给客户端
        ra.setRapwd(""); // 密码置空
        // 在session 中保存用户信息，以维持用户登录状态
        session.setAttribute("resadmin",ra);
        map.put("obj",ra);
        return map;

    }
    @RequestMapping(value = "islogin",method = {RequestMethod.POST,RequestMethod.GET})
    public Map<String,Object> isLogin(HttpSession session){
        Map<String,Object> map = new HashMap<>();
        Resadmin obj = (Resadmin) session.getAttribute("resadmin");
        if(obj != null){
            map.put("code",1);
            map.put("obj",obj);
            return map;
        }
        map.put("code",0);
        return map;
    }
}
