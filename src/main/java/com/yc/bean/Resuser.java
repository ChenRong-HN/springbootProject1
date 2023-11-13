package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 用户
 */
public class Resuser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer userid;
    private String username;
    private String pwd;
    private String email;

    // 与页面传过来的属性yzm对应
    @TableField(exist = false) // 将不是数据库中的属性排除掉
    private String yzm;
}
