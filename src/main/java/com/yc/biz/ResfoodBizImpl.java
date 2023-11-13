package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;
import com.yc.config.RedisKeys;
import com.yc.dao.ResfoodMapper;
import lombok.Builder.Default;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ResfoodBizImpl implements ResfoodBiz{
    @Value("${nginx.address}")
    private String nginxAddress = "http://localhost:8888/";
    @Autowired
    private ResfoodMapper resfoodMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<Resfood> findAll() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("fid");
        return resfoodMapper.selectList(wrapper);
//        return resfoodMapper.selectList(null);
    }

//    @Override
//    public List<Resfood> findByPage(int pageno, int pagesize, String sortby, String sort) {
//
////        return resfoodMapper.findByPage(pageno,pagesize,sortby,sort);
//    }

    @Override
    public Page<Resfood> findByPage(int pageno, int pagesize, String sortby, String sort ) {
        QueryWrapper wrapper = new QueryWrapper();
        if("asc".equalsIgnoreCase(sortby)){
            wrapper.orderByAsc(sortby);
        } else {
            wrapper.orderByDesc(sortby);
        }
        Page<Resfood> page = new Page<>(pageno,pagesize);
        //执行分页查询
        Page<Resfood> resfoodPage = this.resfoodMapper.selectPage(page,wrapper);

        //到redis要查询这些Resfood的浏览数
        List<Resfood> list = resfoodPage.getRecords();
        List<String> keys = new ArrayList<String>();
        for (Resfood resfood : list) {
            keys.add(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + resfood.getFid());
        }
        List<Integer> allFoodDetailCountValues = redisTemplate.opsForValue().multiGet(keys);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setDetail_count(allFoodDetailCountValues.get(i));
            //修改图片的地址
            list.get(i).setFphoto(nginxAddress+list.get(i).getFphoto() );
        }

        log.info("总记录数：" + resfoodPage.getTotal());
        log.info("总页数：" + resfoodPage.getPages());
        log.info("当前页码：" + resfoodPage.getCurrent());
        return resfoodPage;
    }

    @Transactional(
            propagation = Propagation.SUPPORTS, //支持事务环境下运行
            isolation = Isolation.DEFAULT, //隔离级别
            timeout = 2000, //超时时间
            readOnly = false,
            rollbackFor = RuntimeException.class //什么情况下回滚
    )
    @Override
    public Integer addResfood(Resfood resfood) {
        this.resfoodMapper.insert(resfood);
        return resfood.getFid();
    }

    @Override
    public Resfood findById(Integer fid) {
        return resfoodMapper.selectById(fid);
    }
}
