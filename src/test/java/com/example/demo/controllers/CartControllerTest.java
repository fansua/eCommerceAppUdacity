package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;

    private UserRepository userRepo =  mock(UserRepository.class);

    private CartRepository cartRepo = mock(CartRepository.class);

    private ItemRepository itemRepo = mock(ItemRepository.class);


    @Before
    public void setup(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepo );
        TestUtils.injectObjects(cartController, "cartRepository", cartRepo);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepo);
    }

    @Test
    public void  add_to_cart_happy_path(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(1);
        User u = new User();
        u.setUsername("test");
        u.setPassword("password");
        u.setId(1);
        Item i = new Item();
        i.setId(1L);
        i.setDescription("Brand new pair of Air Jordan's 360");
        i.setName("Air Jordan 360");
        i.setPrice(new BigDecimal(1));
        Cart c = new Cart();
        u.setCart(c);
        when(userRepo.findByUsername(modifyCartRequest.getUsername())).thenReturn(u);
        when(itemRepo.findById(modifyCartRequest.getItemId())).thenReturn(Optional.of(i));

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertNotNull(response);

        assertEquals(200, response.getStatusCodeValue());
        Cart n = response.getBody();
        assertEquals(1,n.getItems().size());
    }

    @Test
    public void add_to_cart_null_user(){

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(1);
        User u = null;

        when(userRepo.findByUsername(modifyCartRequest.getUsername())).thenReturn(u);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertNotNull(response);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void add_to_cart_with_empty_item(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(1);
        User u = new User();
        u.setUsername("test");
        u.setPassword("password");
        u.setId(1);
        Optional <Item> i = Optional.empty();
        when(userRepo.findByUsername(modifyCartRequest.getUsername())).thenReturn(u);
        when(itemRepo.findById(modifyCartRequest.getItemId())).thenReturn(i);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        assertNotNull(response);

        assertEquals(404, response.getStatusCodeValue());

    }

    @Test
    public  void remove_from_cart() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(1);
        User u = new User();
        u.setUsername("test");
        u.setPassword("password");
        u.setId(1);
        Item i = new Item();
        i.setId(1L);
        i.setDescription("Brand new pair of Air Jordan's 360");
        i.setName("Air Jordan 360");
        i.setPrice(new BigDecimal(1));
        Cart c = new Cart();
        u.setCart(c);
        when(userRepo.findByUsername(modifyCartRequest.getUsername())).thenReturn(u);
        when(itemRepo.findById(modifyCartRequest.getItemId())).thenReturn(Optional.of(i));

        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        assertNotNull(response);

        assertEquals(200, response.getStatusCodeValue());
        Cart n = response.getBody();
        assertEquals(0,n.getItems().size());
    }

    @Test
    public void remove_from_cart_null_user() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(1);
        User u = null;

        when(userRepo.findByUsername(modifyCartRequest.getUsername())).thenReturn(u);

        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        assertNotNull(response);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void remove_from_cart_no_items(){

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(1);
        User u = new User();
        u.setUsername("test");
        u.setPassword("password");
        u.setId(1);
        Optional <Item> i = Optional.empty();
        when(userRepo.findByUsername(modifyCartRequest.getUsername())).thenReturn(u);
        when(itemRepo.findById(modifyCartRequest.getItemId())).thenReturn(i);

        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        assertNotNull(response);

        assertEquals(404, response.getStatusCodeValue());
    }





}
