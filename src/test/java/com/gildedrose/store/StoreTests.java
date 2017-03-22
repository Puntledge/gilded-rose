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
    public void testBuyOneItem() throws Exception {
        String name = "GR1";
        assert (Store.buyItem(name, 1) != null);
    }

    @Test
    public void testBuyFiveOfAnItem() throws Exception {
        String name = "GR1";
        Integer quantity = 5;
        Integer before = Store.getQuantityInStock(name);
        assert (before > 0);
        assert (Store.buyItem(name, quantity) != null);
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
        assert (price1 < price2);
    }
}
