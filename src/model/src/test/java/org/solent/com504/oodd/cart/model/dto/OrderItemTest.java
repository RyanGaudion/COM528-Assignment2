/*
 * Copyright 2021 rgaud.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
