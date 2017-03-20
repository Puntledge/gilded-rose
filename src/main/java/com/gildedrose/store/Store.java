/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gildedrose.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Store
 *
 * @author Michael Young
 */
public class Store {

    public static class ItemNotFoundException extends Exception {

    }

    public static class UserNotFoundException extends Exception {

    }

    // Simulated DB with 6 items
    private static final Item[] ITEMS = new Item[]{
        new Item("GR1", "Item One", 15),
        new Item("GR2", "Item Two", 25),
        new Item("GR3", "Item Three", 35),
        new Item("GR4", "Item Four", 45),
        new Item("GR5", "Item Five", 55),
        new Item("GR6", "Item Six", 65)
    };

    // Simulated DB with 3 users
    private static final User[] USERS = new User[]{
        new User("abc", "qwerty"),
        new User("def", "asdfgh"),
        new User("ghi", "zxcvbn")
    };

    // Map to track item stock
    private static final HashMap<String, Integer> STOCK = new HashMap<String, Integer>();

    // Map to track item views
    private static final HashMap<String, ArrayList<Date>> VIEWS = new HashMap<String, ArrayList<Date>>();

    /**
     * authenticateUser
     *
     * @param login
     * @param password
     * @return true if authentication successful
     * @throws UserNotFoundException
     */
    public static boolean authenticateUser(String login, String password) throws UserNotFoundException {
        User user = getUser(login);
        if (user == null) {
            return false;
        }
        return user.getPassword().compareTo(password) == 0;
    }

    /**
     * buyItem
     *
     * @param name - item name
     * @param quantity - # of items to buy
     * @param login - authentication
     * @param password - authentication
     * @return true if transaction completed
     * @throws ItemNotFoundException
     * @throws UserNotFoundException
     */
    public static boolean buyItem(String name, Integer quantity, String login, String password) throws ItemNotFoundException, UserNotFoundException {
        if (authenticateUser(login, password) == false) {
            return false;
        }
        Integer quantityInStock = getQuantityInStock(name);
        if (quantityInStock < quantity) {
            return false;
        }
        updateQuantityInStock(name, quantityInStock - quantity);
        return true;
    }

    /**
     * getAdjustedPrice
     *
     * @param name - item name
     * @param price - base price
     * @return adjusted price i.e. base price plus 10% if item has been viewed
     * more than 10 times in the last hour
     */
    public static Integer getAdjustedPrice(String name, Integer price) {
        Date now = new Date();
        ArrayList<Date> views = VIEWS.get(name);
        if (views != null) {
            int n = 0;
            for (Date date : views) {
                if ((now.getTime() - date.getTime()) < 3600000) { // 1 hour in milliseconds
                    n++;
                    if (n > 10) {
                        return (110 * price) / 100;
                    }
                }
            }
        }
        return price;
    }

    /**
     * getItem
     *
     * @param name - item name
     * @param view - if true a timestamp is added which will be used to
     * determine surge pricing
     * @return item matching name
     * @throws ItemNotFoundException
     */
    public static Item getItem(String name, boolean view) throws ItemNotFoundException {
        for (Item item : getItems()) {
            if (item.getName().compareTo(name) == 0) {
                if (view) {
                    ArrayList<Date> views = VIEWS.get(name);
                    if (views == null) {
                        views = new ArrayList<>();
                        VIEWS.put(name, views);
                    }
                    views.add(new Date());
                }
                return item;
            }
        }
        throw new ItemNotFoundException();
    }

    /**
     * getItems
     *
     * @return all items
     */
    public static Item[] getItems() {
        return ITEMS;
    }

    /**
     * getQuantityInStock
     *
     * @param name
     * @return quantity of items matching name
     */
    public static Integer getQuantityInStock(String name) {
        Integer quantity = STOCK.get(name);
        if (quantity == null) {
            quantity = 10;
            STOCK.put(name, quantity);
        }
        return quantity;
    }

    /**
     * getUser
     *
     * @param login
     * @return user matching login
     * @throws UserNotFoundException
     */
    public static User getUser(String login) throws UserNotFoundException {
        for (User user : getUsers()) {
            if (user.getLogin().compareTo(login) == 0) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }

    /**
     * getUsers
     *
     * @return all users
     */
    public static User[] getUsers() {
        return USERS;
    }

    /**
     * getViews
     *
     * @return
     */
    public static HashMap<String, ArrayList<Date>> getViews() {
        return VIEWS;
    }

    /**
     * updateQuantityInStock
     *
     * @param name
     * @param newQuantity
     */
    public static void updateQuantityInStock(String name, Integer newQuantity) {
        STOCK.put(name, newQuantity);
    }
}
