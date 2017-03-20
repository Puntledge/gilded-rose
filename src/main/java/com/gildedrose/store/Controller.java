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
     * @param name
     * @return item matching name or null if not found
     */
    @GetMapping("/item/{name}")
    public Item getItem(@PathVariable String name) {
        try {
            return Store.getItem(name, true); // mark item as viewed
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * getUsers
     *
     * @return all users
     */
    @GetMapping("/users")
    public User[] getUsers() {
        return Store.getUsers();
    }

    /**
     * getViews
     *
     * @return all views
     */
    @GetMapping("/views")
    public HashMap<String, ArrayList<Date>> getViews() {
        return Store.getViews();
    }

    /**
     * buyItem
     *
     * @param name
     * @param quantity
     * @param login
     * @param password
     * @return item matching name or null if not found
     */
    @PostMapping("/item/{name}/{quantity}")
    public boolean postItem(@PathVariable String name, @PathVariable Integer quantity, @RequestParam String login, @RequestParam String password) {
        boolean b = false;
        try {
            b = Store.buyItem(name, quantity, login, password);
        } catch (Store.ItemNotFoundException infe) {
            b = false;
        } catch (Store.UserNotFoundException unfe) {
            b = false;
        }
        return b;
    }
}
