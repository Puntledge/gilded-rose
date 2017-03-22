/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gildedrose.store;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
     * @return item matching name
     * @throws Store.ItemNotFoundException
     */
    @GetMapping("/item/{name}")
    public Item getItem(@PathVariable String name) throws Store.ItemNotFoundException {
        return Store.getItem(name, true); // mark item as viewed
    }

    /**
     * buyItem
     *
     * @param name - item name
     * @param quantity - how many of this item to buy
     * @return item matching name
     * @throws Store.ItemNotFoundException
     */
    @PostMapping("/item/{name}/{quantity}")
    public Item postItem(@PathVariable String name, @PathVariable Integer quantity) throws Store.ItemNotFoundException {
        return Store.buyItem(name, quantity);
    }
}
