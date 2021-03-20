package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;

    private ItemRepository itemRepo = mock(ItemRepository.class);

    @Before
    public void setup() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepo);
    }


    @Test
    public void get_item_happy_path(){
        Item i = new Item();
        i.setId(1L);
        i.setDescription("Brand new pair of Air Jordan's 360");
        i.setName("Air Jordan 360");
        i.setPrice(new BigDecimal(1));
        when(itemRepo.findAll()).thenReturn(Arrays.asList(i));

        ResponseEntity<List<Item>> response = itemController.getItems();

        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        Item responseItem = response.getBody().get(0);
        assertEquals(i,responseItem);

    }

    @Test
    public void get_item_by_id_happy_path(){
        Long id = 1L;
        Item i = new Item();
        i.setId(id);
        i.setDescription("Brand new pair of Air Jordan's 360");
        i.setName("Air Jordan 360");
        i.setPrice(new BigDecimal(1));
        when(itemRepo.findById(id)).thenReturn(Optional.of(i));

        ResponseEntity<Item> response = itemController.getItemById(id);

        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        Item responseItem = response.getBody();
        assertEquals(i,responseItem);

    }

    @Test
    public void get_item_by_name_happy_path(){
        Long id = 1L;
        String name = "Air Jordan 360";
        Item i = new Item();
        i.setId(id);
        i.setDescription("Brand new pair of Air Jordan's 360");
        i.setName(name);
        i.setPrice(new BigDecimal(1));
        when(itemRepo.findByName(name)).thenReturn(Arrays.asList(i));

        ResponseEntity<List<Item>> response = itemController.getItemsByName(name);

        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        Item responseItem = response.getBody().get(0);
        assertEquals(i,responseItem);

    }

    @Test
    public void get_item_by_name_null_items(){

        when(itemRepo.findByName("Air Jordan 360")).thenReturn(null);

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Air Jordan 360");

        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());


    }

    @Test
    public void get_item_by_name_empty_items(){

        when(itemRepo.findByName("Air Jordan 360")).thenReturn(new ArrayList<>());

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Air Jordan 360");

        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());


    }
}
