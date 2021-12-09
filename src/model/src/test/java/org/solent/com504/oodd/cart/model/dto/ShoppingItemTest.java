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
public class ShoppingItemTest {
    
    /**
     * Tests all setters and getters
     */
    @Test
    public void testSetGetValues() {
        ShoppingItem item = new ShoppingItem();
        item.setCategory("Category 1");
        item.setDeactivated(true);
        item.setFilename("localFile.png");
        item.setId(10L);
        item.setName("item name");
        item.setPrice(10.0);
        item.setQuantity(12);
        
        assertEquals("Category 1", item.getCategory());
        assertEquals(true, item.getDeactivated());
        assertEquals("localFile.png", item.getFilename());
        assertEquals("10", item.getId().toString());        
        assertEquals("item name", item.getName());        
        assertEquals(10.0, item.getPrice(), 0);
        assertEquals(Integer.valueOf(12), item.getQuantity());
    }
    
}
