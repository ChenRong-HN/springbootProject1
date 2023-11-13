package com.yc.biz;

import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.bean.CartItem;

import java.util.Set;

public interface ResorderBiz {
    /**
     * 添加订单
     * @param resorder  订单信息
     * @param cartItems  购物项信息  Set
     * @param resuser  当前下订单的用户
     * @return
     */
    public int order(Resorder resorder, Set<CartItem> cartItems, Resuser resuser);
}
