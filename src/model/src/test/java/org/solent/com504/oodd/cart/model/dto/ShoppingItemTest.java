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
