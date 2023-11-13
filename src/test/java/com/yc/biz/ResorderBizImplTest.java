package com.yc.biz;

import com.yc.bean.CartItem;
import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ResorderBizImplTest {

    @Autowired
    private ResorderBiz resorderBiz;
    @Autowired
    private ResfoodBiz resfoodBiz;
    @Autowired
    private ResuserBiz resuserBiz;
    @Test
    public void order() {
        Resorder resorder = new Resorder();
        resorder.setStatus(0);
        resorder.setOrdertime("2023-10-23 12:37");
        resorder.setDeliverytime("2023-10-23 13:20");
        resorder.setAddress("湖南工学院");
        resorder.setPs("请尽快配送！");
        resorder.setTel("15204896532");
        Set<com.yc.bean.CartItem> cartItems = new HashSet<CartItem>();
        CartItem cartItem1 = new CartItem();
        cartItem1.setNum(1);
        cartItem1.setFood(resfoodBiz.findById(1));

        CartItem cartItem2 = new CartItem();
        cartItem2.setNum(2);
        cartItem2.setFood(resfoodBiz.findById(2));
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);

        Resuser user = this.resuserBiz.findById(1);

        resorderBiz.order(resorder, cartItems, user);
    }
}