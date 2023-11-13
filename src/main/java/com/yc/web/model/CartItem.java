package com.yc.web.model;

import com.yc.bean.Resfood;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem implements Serializable {
    private Resfood food; // 购买的商品
    private Integer num; // 这个商品的数量
    private Double smallCount; // 小计
    public Double getSmallCount(){
        if (food != null){
            smallCount = this.food.getRealprice() * this.num;
        }
        return smallCount;
    }
}

