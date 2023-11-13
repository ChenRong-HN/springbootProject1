package com.yc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.biz.FastDFSBiz;
import com.yc.biz.ResfoodBiz;
import com.yc.dao.ResfoodMapper;
import com.yc.web.model.MyPageBean;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("back/resfood")
@Slf4j
@Api(tags = "后台菜品管理")
public class BackResfoodController {
    @Autowired
    private ResfoodBiz resfoodBiz;

    @Autowired
    private FastDFSBiz fastDFSBiz;
    @Autowired
    private ResfoodMapper resfoodMapper;
    @RequestMapping(value = "/addNewFood",method = RequestMethod.POST)
    public Map<String,Object> addNewFood(String fname,
                                         Double normprice,
                                         Double realprice,
                                         String detail,
                                         MultipartFile fphoto){
        Map<String,Object> map = new HashMap<>();
        Resfood rf = new Resfood();
        try{
            //TODO 步骤一: 将图片上传到 fastDFS 中，返回图片地址到
            String path = this.fastDFSBiz.uploadFile(fphoto);
            //步骤二: 操作数据库
            rf.setFname(fname);
            rf.setNormprice(normprice);
            rf.setRealprice(realprice);
            rf.setDetail(detail);
            rf.setFphoto(path);
            resfoodMapper.insert(rf);
        }catch (Exception e){
            e.printStackTrace();
            map.put("ocde",0);
            map.put("msg",e.getMessage());
            return map;
        }
        map.put("code",1);
        map.put("obj",rf);
        return map;

    }
    @RequestMapping(value = "/findByPage",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object> findByPage(@RequestParam int pageno, @RequestParam int pagesize,
                                         @RequestParam(required = false) String sortby, @RequestParam(required = false) String sort){
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
}
