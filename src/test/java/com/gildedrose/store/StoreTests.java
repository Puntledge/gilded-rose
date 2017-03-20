/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gildedrose.store;

import org.junit.Test;

/**
 * StoreTests
 *
 * @author Michael Young
 */
public class StoreTests {

    @Test
    public void testAuthenticateUsers() throws Exception {
        for (User user : Store.getUsers()) {
            boolean b = Store.authenticateUser(user.getLogin(), user.getPassword());
            assert (b);
        }
    }

    @Test
    public void testBuyItemOnce() throws Exception {
        String name = "GR1", login = "abc", password = "qwerty";
        assert(Store.buyItem(name, 1, login, password));
    }

    @Test
    public void testBuyItemFiveTimes() throws Exception {
        String name = "GR1", login = "abc", password = "qwerty";
        Integer quantity = 5;
        Integer before = Store.getQuantityInStock(name);
        assert (before > 0);
        assert(Store.buyItem(name, quantity, login, password));
        Integer after = Store.getQuantityInStock(name);
        assert (after == (before - quantity));
    }

    @Test
    public void testGetItem() throws Exception {
        String name = "GR1";
        Integer price1 = 0, price2 = 0;
        for (int i = 0; i < 5; i++) {
            Item item = Store.getItem(name, true);
            if (i > 0) {
                price2 = item.getPrice();
            } else {
                price1 = item.getPrice();
            }
        }
        assert (price1 == price2);
    }

    @Test
    public void testGetItemWithSurgePricing() throws Exception {
        String name = "GR1";
        Integer price1 = 0, price2 = 0;
        for (int i = 0; i < 15; i++) {
            Item item = Store.getItem(name, true);
            if (i > 0) {
                price2 = item.getPrice();
            } else {
                price1 = item.getPrice();
            }
        }
        assert (price1 != price2);
    }
}
