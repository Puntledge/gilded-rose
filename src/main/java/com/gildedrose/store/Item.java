/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gildedrose.store;

/**
 * Item
 *
 * @author Michael
 */
public class Item {

    private String name;
    private String description;

    private Integer price;

    public Item(String name, String description, Integer price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getPrice() {
        return Store.getAdjustedPrice(this.name, this.price); // ask store to adjust item price based on surge conditions, discounts, etc.
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
