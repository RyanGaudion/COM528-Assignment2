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
package org.solent.com504.oodd.cart.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.cart.model.dto.OrderItem;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;

/**
 *
 * @author cgallen
 */
public class ShoppingCartImpl implements ShoppingCart {

    private HashMap<String, OrderItem> itemMap = new HashMap<String, OrderItem>();

    private static final Logger LOG = LogManager.getLogger(ShoppingCartImpl.class);   
    
    @Override
    public List<OrderItem> getShoppingCartItems() {
        List<OrderItem> itemlist = new ArrayList();
        for (String itemUUID : itemMap.keySet()) {
            OrderItem shoppingCartItem = itemMap.get(itemUUID);
            LOG.info(shoppingCartItem);            
            LOG.info(shoppingCartItem.getItem());
            itemlist.add(shoppingCartItem);
        }
        return itemlist;
    }

    @Override
    public void addItemToCart(ShoppingItem shoppingItem) {
        // itemMap.put(shoppingItem.getUuid(), shoppingItem);
        
        // ANSWER
        boolean itemExists = false;
        for (String itemUUID : itemMap.keySet()) {
            
            OrderItem shoppingCartItem = itemMap.get(itemUUID);
            
            if (shoppingCartItem.getItem().getName().equals(shoppingItem.getName())){
                Integer q = shoppingCartItem.getQuantity();
                shoppingCartItem.setQuantity(q+1);
                itemExists = true;
                break;
            }
        }
        if (!itemExists){
            OrderItem newShoppingCartItem = new OrderItem(shoppingItem, 1);
            itemMap.put(shoppingItem.getUuid(), newShoppingCartItem);
        }
    }

    @Override
    public void removeItemFromCart(String itemUuid) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // ANSWER
        itemMap.remove(itemUuid);
    }
    
    @Override
    public void clearCart() {
        itemMap.clear();
    }

    @Override
    public double getTotal() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        // ANSWER
        double total = 0;

        for (String itemUUID : itemMap.keySet()) {
            OrderItem shoppingCartItem = itemMap.get(itemUUID);
            total = total + shoppingCartItem.getItem().getPrice() * shoppingCartItem.getQuantity();
        }

        return total;

    }

}
