package com.yc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.bean.Rescart;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface RescartMapper extends BaseMapper<Rescart> {
    @Select("select * from rescart")
    List<Rescart> findByName(String username);

    // 加入购物车时检查该订单是否存在，存在  增加数量 ， 不存在  创建新的购物车
    @Select("select count(*) from rescart where username=#{username} and fname=#{fname}")
    int check(@Param("username") String username,@Param("fname") String fname);

    @Update("update rescart set num=num+#{num} where username=#{username} and fname=#{fname}")
    int updateNum(@Param("username") String username,@Param("fname") String fname,@Param("num") int num);

}
