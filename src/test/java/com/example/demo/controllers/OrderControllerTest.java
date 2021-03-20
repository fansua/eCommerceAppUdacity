package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;

    private UserRepository userRepo = mock(UserRepository.class);
    private OrderRepository orderRepo = mock(OrderRepository.class);

    @Before
    public void setup() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepo);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepo);
    }

    @Test
    public void submit_order_happy_path(){
        String username = "test";
        Item i = new Item();
        i.setId(1L);
        i.setDescription("Brand new pair of Air Jordan's 360");
        i.setName("Air Jordan 360");
        i.setPrice(new BigDecimal(1));
        Cart c = new Cart();
        c.addItem(i);
        User u = new User();
        u.setId(1);
        u.setPassword("password");
        u.setUsername(username);
        u.setCart(c);
        when(userRepo.findByUsername(username)).thenReturn(u);

        ResponseEntity<UserOrder> response = orderController.submit(username);

        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        UserOrder ur = response.getBody();
        assertNotNull(ur);
        assertEquals(i,ur.getItems().get(0));

    }

    @Test
    public void submit_order_sad_path() {
        String username = "test";
        when(userRepo.findByUsername(username)).thenReturn(null);
        ResponseEntity<UserOrder> response = orderController.submit(username);
        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());
    }

    @Test
    public void get_order_for_user_sad_path() {
        String username = "test";
        when(userRepo.findByUsername(username)).thenReturn(null);
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(username);
        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());
    }

    @Test
    public void get_order_for_user_happy_path(){
        String username = "test";
        User u = new User();
        u.setId(1);
        u.setPassword("password");
        u.setUsername(username);
        when(userRepo.findByUsername(username)).thenReturn(u);
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser(username);
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
    }

}
