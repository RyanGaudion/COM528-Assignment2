/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.model.dto;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rgaud
 */
public class OrderItemTest {
    
    /**
     * Test of setId method, of class OrderItem.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = 10L;
        OrderItem instance = new OrderItem();
        instance.setId(id);
        assertEquals(Long.valueOf(10L), instance.getId());
    }

    /**
     * Test of setItem method, of class OrderItem.
     */
    @Test
    public void testSetItem() {
        System.out.println("setItem");
        ShoppingItem item = new ShoppingItem();
        item.setName("cat");
        OrderItem instance = new OrderItem();
        instance.setItem(item);
        assertEquals("cat", instance.getItem().getName());
    }


    /**
     * Test of setQuantity method, of class OrderItem.
     */
    @Test
    public void testSetQuantity() {
        System.out.println("setQuantity");
        int quantity = 5;
        OrderItem instance = new OrderItem();
        instance.setQuantity(quantity);
        assertEquals(5, instance.getQuantity());
    }
    
}
