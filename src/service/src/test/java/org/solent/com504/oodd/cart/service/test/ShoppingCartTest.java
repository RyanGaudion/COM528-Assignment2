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
     * tests GetItems functionality
     */
    @Test
    public void GetItemsTest() {
        assertNotNull(shoppingCart);
        
        List<OrderItem> items = shoppingCart.getShoppingCartItems();
        
        assertTrue(items.isEmpty());
        
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setName("fred");
        shoppingItem.setUuid("a1");
        
        shoppingCart.addItemToCart(shoppingItem);
        
        assertEquals(1, shoppingCart.getShoppingCartItems().size() );
    }
    
     /**
     * tests Add items functionality
     */
    @Test
    public void AddItemsTest() {
        
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setName("fred");
        shoppingItem.setUuid("b1");
        ShoppingItem shoppingItem2 = new ShoppingItem();
        shoppingItem2.setName("fred2");
        shoppingItem2.setUuid("b2");
        
        shoppingCart.addItemToCart(shoppingItem);        
        shoppingCart.addItemToCart(shoppingItem2);

        assertEquals(2, shoppingCart.getShoppingCartItems().size() );
    }
    
    /**
     * tests Remove Items
     */
    @Test
    public void RemoveItemsTest() {
        
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setName("fred");
        shoppingItem.setUuid("c1");
        ShoppingItem shoppingItem2 = new ShoppingItem();
        shoppingItem2.setName("fred3");
        shoppingItem2.setUuid("c2");
        
        shoppingCart.addItemToCart(shoppingItem);        
        shoppingCart.addItemToCart(shoppingItem2);

        assertEquals(2, shoppingCart.getShoppingCartItems().size() );
        
        shoppingCart.removeItemFromCart("c2");
        
        assertEquals(1, shoppingCart.getShoppingCartItems().size() );
    }
    
    /**
     * tests Clear Cart
     */
    @Test
    public void ClearItemsTest() {
        
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setName("fred");
        shoppingItem.setUuid("d1");
        ShoppingItem shoppingItem2 = new ShoppingItem();
        shoppingItem2.setName("fred2");
        shoppingItem2.setUuid("d2");
        
        shoppingCart.addItemToCart(shoppingItem);        
        shoppingCart.addItemToCart(shoppingItem2);

        assertEquals(2, shoppingCart.getShoppingCartItems().size() );
        
        shoppingCart.clearCart();
        
        assertEquals(0, shoppingCart.getShoppingCartItems().size() );
    }
    
    /**
     * tests Get total
     */
    @Test
    public void GetTotalTest() {
        
        ShoppingItem shoppingItem = new ShoppingItem();
        shoppingItem.setName("fred");
        shoppingItem.setUuid("e1");
        shoppingItem.setPrice(4.00);
        ShoppingItem shoppingItem2 = new ShoppingItem();
        shoppingItem2.setName("fred2");
        shoppingItem2.setPrice(6.00);
        shoppingItem2.setUuid("e2");
        
        shoppingCart.addItemToCart(shoppingItem);        
        shoppingCart.addItemToCart(shoppingItem2);        
        shoppingCart.addItemToCart(shoppingItem2);


        assertEquals(2, shoppingCart.getShoppingCartItems().size() );        
        assertEquals(1, shoppingCart.getShoppingCartItems().get(0).getQuantity() );        
        assertEquals(2, shoppingCart.getShoppingCartItems().get(1).getQuantity() );


        
        assertEquals(16.00, shoppingCart.getTotal(), 0);
    }
}
