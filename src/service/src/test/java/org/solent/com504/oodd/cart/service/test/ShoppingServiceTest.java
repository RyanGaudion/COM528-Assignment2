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
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;
import org.solent.com504.oodd.cart.model.service.ShoppingService;
import org.solent.com504.oodd.cart.service.ServiceObjectFactory;
import org.solent.com504.oodd.cart.service.ShoppingCartImpl;
import org.solent.com504.oodd.cart.service.spring.test.ServiceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 *
 * @author cgallen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@Transactional
@DependsOn({"populate-database"})
public class ShoppingServiceTest {
    
    @Autowired
    ShoppingService shoppingService;
    
    /**
     * Test shopping service is not null
     */
    @Test
    public void test1() {
        assertNotNull(shoppingService);
    }
    
    /**
     * test get available items on the shopping service
     */
    @Test
    public void GetAllTest(){
        List<ShoppingItem> items = shoppingService.getAvailableItems();
        assertEquals(8, items.size());
    }
    
    /*
    * test the check stock method against a valid cart
    */
    @Test
    public void CheckValidStockTest(){
        ShoppingCart cart = new ShoppingCartImpl();
        List<ShoppingItem> items = shoppingService.searchAvailableItems("cat");
        cart.addItemToCart(items.get(0));
        
        assertEquals("", shoppingService.checkStock(cart));
    }
    
    /*
    * test the check stock method against a non-valid cart
    */
    @Test
    public void CheckInValidStockTest(){
        ShoppingCart cart = new ShoppingCartImpl();
        List<ShoppingItem> items = shoppingService.searchAvailableItems("pen");
        cart.addItemToCart(items.get(0));
        
        assertTrue(shoppingService.checkStock(cart).length() > 0);
    }
    
    /**
     * test search available items on the shopping service
     */
    @Test
    public void SearchTest(){
        List<ShoppingItem> items = shoppingService.searchAvailableItems("pen");
        assertEquals(1, items.size());
    }
    
    /**
     * test get new item method
     */
    @Test
    public void NewItemTest(){
        ShoppingItem items = shoppingService.getNewItemByName("pen");
        assertEquals((Integer)0, items.getQuantity());
    }
}
