package com.maxmind.minfraud.input;

import com.maxmind.minfraud.input.ShoppingCartItem.Builder;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShoppingCartTest {

    @Test
    public void testCategory() throws Exception {
        ShoppingCartItem item = new Builder().category("cat1").build();
        assertEquals("cat1", item.getCategory());
    }

    @Test
    public void testItemId() throws Exception {
        ShoppingCartItem item = new Builder().itemId("id5").build();
        assertEquals("id5", item.getItemId());
    }

    @Test
    public void testQuantity() throws Exception {
        ShoppingCartItem item = new Builder().quantity(100).build();
        assertEquals(100, item.getQuantity());
    }

    @Test
    public void testPrice() throws Exception {
        ShoppingCartItem item = new Builder().price(BigDecimal.TEN).build();
        assertEquals(BigDecimal.TEN, item.getPrice());
    }

    @Test
    public void testDoublePrice() throws Exception {
        ShoppingCartItem item = new Builder().price(10.3).build();
        assertEquals(BigDecimal.valueOf(10.3), item.getPrice());
    }
}