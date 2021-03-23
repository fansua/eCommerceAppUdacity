package com.example.demo.model.persistence;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class UserOrderTest {

private UserOrder userOrder;
    @Before
    public void setup(){
        userOrder = new UserOrder();

    }

    @Test
    public void user_order_creation(){
        userOrder.setId(100L);
        userOrder.setTotal(new BigDecimal(2));
        userOrder.setUser(null);
        userOrder.setItems(null);

        Item i = new Item();
        i.setId(1L);
        i.setDescription("Brand new pair of Air Jordan's 360");
        i.setName("Air Jordan 360");
        i.setPrice(new BigDecimal(1));
        Cart c = new Cart();
        c.addItem(i);
         UserOrder  uo = userOrder.createFromCart(c);

         assertNotNull(uo);
         assertEquals(new BigDecimal(2),userOrder.getTotal());
         assertNull(userOrder.getItems());
         assertNull(userOrder.getUser());
    }

}
