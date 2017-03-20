/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gildedrose.store;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * ControllerTests
 *
 * @author Michael Young
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class ControllerTests {

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new Controller()).build();
    }

    @Test
    public void testGetItems() throws Exception {
        ResultActions ra = this.mockMvc.perform(get("/items").accept(MediaType.parseMediaType(CONTENT_TYPE)));
        ra.andExpect(content().contentType(CONTENT_TYPE));
        ra.andExpect(status().isOk());
        ra.andExpect(jsonPath("$").isArray());
        ra.andExpect(jsonPath("$[0].name").value("GR1"));
        ra.andExpect(jsonPath("$[0].description").value("Item One"));
        ra.andExpect(jsonPath("$[0].price").value(15));
    }

    @Test
    public void testGetItem() throws Exception {
        ResultActions ra = this.mockMvc.perform(get("/item/GR1").accept(MediaType.parseMediaType(CONTENT_TYPE)));
        ra.andExpect(content().contentType(CONTENT_TYPE));
        ra.andExpect(status().isOk());
        ra.andExpect(jsonPath("$.name").value("GR1"));
        ra.andExpect(jsonPath("$.description").value("Item One"));
        ra.andExpect(jsonPath("$.price").value(15));
    }

    @Test
    public void testGetItemWithInvalidName() throws Exception {
        ResultActions ra = this.mockMvc.perform(get("/item/invalid").accept(MediaType.parseMediaType(CONTENT_TYPE)));
//      ra.andExpect(content().contentType(CONTENT_TYPE));
//      ra.andExpect(status().isOk());
    }

    @Test
    public void testGetUsers() throws Exception {
        ResultActions ra = this.mockMvc.perform(get("/users").accept(MediaType.parseMediaType(CONTENT_TYPE)));
        ra.andExpect(content().contentType(CONTENT_TYPE));
        ra.andExpect(status().isOk());
        ra.andExpect(jsonPath("$").isArray());
        ra.andExpect(jsonPath("$[0].login").value("abc"));
        ra.andExpect(jsonPath("$[0].password").value("qwerty"));
    }

    @Test
    public void testPostItem() throws Exception {
        ResultActions ra = this.mockMvc.perform(post("/item/GR1/1").accept(MediaType.parseMediaType(CONTENT_TYPE)));
//      ra.andExpect(content().contentType(CONTENT_TYPE));
        ra.andExpect(status().is(400));
    }

    @Test
    public void testPostItemWithLoginAndPassword() throws Exception {
        ResultActions ra = this.mockMvc.perform(post("/item/GR1/1?login=abc&password=qwerty").accept(MediaType.parseMediaType(CONTENT_TYPE)));
        ra.andExpect(content().contentType(CONTENT_TYPE));
        ra.andExpect(status().isOk());
    }

    @Test
    public void testPostItemWithInvalidLoginAndPassword() throws Exception {
        ResultActions ra = this.mockMvc.perform(post("/item/GR1/1?login=invalid&password=invalid").accept(MediaType.parseMediaType(CONTENT_TYPE)));
//      ra.andExpect(content().contentType(CONTENT_TYPE));
//      ra.andExpect(status().isOk());
    }
}
