/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gildedrose.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller
 *
 * @author Michael Young
 */
@RestController
public class Controller {

    /**
     * getItems
     *
     * @return all items
     */
    @GetMapping("/items")
    public Item[] getItems() {
        return Store.getItems();
    }

    /**
     * getItem
     *
     * @param name - item name
     * @return item matching name or null if not found
     */
    @GetMapping("/item/{name}")
    public Item getItem(@PathVariable String name) {
        try {
            return Store.getItem(name, true); // mark item as viewed
        } catch (Store.ItemNotFoundException e) {
        }
        return null;
    }

    /**
     * getUsers - for debugging only
     *
     * @return all users
    @GetMapping("/users")
    public User[] getUsers() {
        return Store.getUsers();
    }
     */

    /**
     * getViews - for debugging only
     *
     * @return all views
    @GetMapping("/views")
    public HashMap<String, ArrayList<Date>> getViews() {
        return Store.getViews();
    }
     */

    /**
     * buyItem
     *
     * @param name - item name
     * @param quantity - number of this item to buy
     * @param login - authentication
     * @param password - authentication
     * @return true if transaction successful; false if item or user not found or insufficient stock
     */
    @PostMapping("/item/{name}/{quantity}")
    public boolean postItem(@PathVariable String name, @PathVariable Integer quantity, @RequestParam String login, @RequestParam String password) {
        boolean b;
        try {
            b = Store.buyItem(name, quantity, login, password);
        } catch (Store.ItemNotFoundException | Store.UserNotFoundException infe) {
            b = false;
        }
        return b;
    }
}
