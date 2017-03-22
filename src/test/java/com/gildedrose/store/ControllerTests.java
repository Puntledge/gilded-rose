/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gildedrose.store;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.Filter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * ControllerTests
 *
 * @author Michael Young
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class ControllerTests {

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String INVALID = "invalid";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private Filter filter;

    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new Controller())
                .build();
    }

    @Test
    public void testGetItems() throws Exception {
        this.mockMvc.perform(get("/items").accept(MediaType.parseMediaType(CONTENT_TYPE)))
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("GR1"))
                .andExpect(jsonPath("$[0].description").value("Item One"))
                .andExpect(jsonPath("$[0].price").value(15));
    }

    @Test
    public void testGetItemWithValidName() throws Exception {
        this.mockMvc.perform(get("/item/GR1").accept(MediaType.parseMediaType(CONTENT_TYPE)))
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("GR1"))
                .andExpect(jsonPath("$.description").value("Item One"))
                .andExpect(jsonPath("$.price").value(15));
    }

    @Test
    public void testGetItemWithInvalidName() throws Exception {
        this.expectedException.expect(Exception.class); // TODO Get this to work with Store.ItemNotFoundException
        this.mockMvc.perform(get("/item/" + INVALID).accept(MediaType.parseMediaType(CONTENT_TYPE)))
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(status().is(500))
                .andExpect(jsonPath("$.message").value("Item " + INVALID + " not found"));
    }

    @Test
    public void testPostItemWithValidName() throws Exception {
        this.mockMvc.perform(post("/item/GR1/1").accept(MediaType.parseMediaType(CONTENT_TYPE)))
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.message").value("Full authentication is required to access this resource"));
    }

    @Test
    public void testPostItemWithInvalidName() throws Exception {
        this.expectedException.expect(Exception.class); // TODO Get this to work with Store.ItemNotFoundException
        this.mockMvc.perform(post("/item/" + INVALID + "/1").accept(MediaType.parseMediaType(CONTENT_TYPE)))
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.message").value("Full authentication is required to access this resource"));
    }

    @Test
    public void testPostItemWithValidLoginAndPassword() throws Exception {
        this.mockMvc.perform(post("/item/GR1/1").with(user("abc").password("qwerty").roles("USER")).accept(MediaType.parseMediaType(CONTENT_TYPE)))
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostItemWithInvalidLoginAndPassword() throws Exception {
        this.mockMvc.perform(post("/item/GR1/1").with(user(INVALID).password(INVALID).roles("USER")).accept(MediaType.parseMediaType(CONTENT_TYPE)))
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(status().is(401))
                .andExpect(jsonPath("$.message").value("Bad credentials"));
    }
}
