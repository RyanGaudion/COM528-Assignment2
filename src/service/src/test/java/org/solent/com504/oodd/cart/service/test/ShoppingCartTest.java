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
package org.solent.com504.oodd.cart.service.test;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.oodd.cart.model.dto.OrderItem;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;
import org.solent.com504.oodd.cart.service.ServiceObjectFactory;

/**
 *
 * @author cgallen
 */

public class ShoppingCartTest {

    ShoppingCart shoppingCart = null;

    /**
     * Setup shopping cart for test
     */
    @Before
    public void before() {
        shoppingCart = ServiceObjectFactory.getNewShoppingCart();
        shoppingCart.getShoppingCartItems().clear();
    }

    /**
     * test cart is not null
     */
    @Test
    public void test1() {
        assertNotNull(shoppingCart);
    }

    /**
     * test functionality of shopping cart
     */
    @Test
    public void testshoppingcart() {
        assertNotNull(shoppingCart);
        
        List<OrderItem> items = shoppingCart.getShoppingCartItems();
        
        assertTrue(items.isEmpty());
        
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setName("fred");
        
        shoppingCart.addItemToCart(shoppingItem);
        
        assertEquals(1, shoppingCart.getShoppingCartItems().size() );

    }
}
