package com.example.demo.model.requests;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ModifyCartRequestTest {

    private ModifyCartRequest modifyCartRequest;

    @Before
    public void setup(){
        modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setItemId(1);
    }

    public void modify_cart_request_test_success_creation() {
        assertNotNull(modifyCartRequest);
        assertEquals("test", modifyCartRequest.getUsername());
        assertEquals(1, modifyCartRequest.getQuantity());
        assertEquals(1, modifyCartRequest.getItemId());
    }
}
