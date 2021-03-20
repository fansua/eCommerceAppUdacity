package com.example.demo.model.requests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateUserRequestTest {

    private CreateUserRequest createUserRequest;

    @Before
    public void setup() {
        createUserRequest = new CreateUserRequest();
        createUserRequest.setConfirmPassword("password");
        createUserRequest.setPassword("password");
        createUserRequest.setUsername("test");
    }

    @Test
    public void create_user_request_test_success_creation() {
        assertNotNull(createUserRequest);
        assertEquals("test", createUserRequest.getUsername());
        assertEquals("password", createUserRequest.getPassword());
        assertEquals("password", createUserRequest.getConfirmPassword());
    }
}
