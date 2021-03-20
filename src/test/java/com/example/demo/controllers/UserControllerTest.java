package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepo = mock(UserRepository.class);

    private CartRepository cartRepo = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp(){
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepo );
        TestUtils.injectObjects(userController, "cartRepository", cartRepo);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void create_user_happy_path() throws Exception {
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");

        ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());

    }

    @Test
    public void create_user_with_min_password_error() throws Exception {
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("test");
        r.setConfirmPassword("test");

        ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void create_user_with_non_matching_pasword() throws Exception {
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("test");
        r.setConfirmPassword("testt");

        ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(400,response.getStatusCodeValue());
    }

    @Test
    public void find_user_by_id_happy_path() throws Exception {
        Long id = 100L;
        User u = new User();
        u.setId(id);
        u.setPassword("password");
        u.setUsername("test");
        when(userRepo.findById(id)).thenReturn(Optional.of(u));

        ResponseEntity<User> response = userController.findById(id);

        assertNotNull(response);
        User responseUser = response.getBody();
        assertEquals("test",responseUser.getUsername());

    }

    @Test
    public void find_user_username_happy_path() throws Exception {
        String username = "test";
        User u = new User();
        u.setId(101);
        u.setPassword("password");
        u.setUsername(username);
        when(userRepo.findByUsername(username)).thenReturn(u);

        ResponseEntity<User> response = userController.findByUserName(username);

        assertNotNull(response);
        User responseUser = response.getBody();
        assertEquals(username,responseUser.getUsername());

    }


}
