package com.yc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rescart implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String fname;
    private Integer num;
    private Integer price;
}
