package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 订单
 */
public class Resorder implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer roid;
    private Integer userid;
    private String address;
    private String tel;

    private String ordertime;
    private String deliverytime;
    private String ps;
    private Integer status;
}
