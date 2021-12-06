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
package org.solent.com504.oodd.cart.model.service;

import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import java.util.List;
import org.solent.com504.oodd.bank.Card;
import org.solent.com504.oodd.cart.model.dto.User;

/**
 *
 * @author cgallen
 */
public interface ShoppingService {
    
    /**
     * Gets all items available 
     * @return list of all items
     */
    public List<ShoppingItem> getAvailableItems();        

    /**
     * Filters down all items by the search query
     * @param searchQuery name of item to filter by
     * @return a filtered list of all items 
     */
    public List<ShoppingItem> searchAvailableItems(String searchQuery);

    /**
     * Checks all items in a cart to make sure there is enough of each item in stock
     * @param cart cart to check the stock of
     * @return an error message if an item is not in stock
     */
    public String checkStock(ShoppingCart cart);
        
    /**
     * Sends the bank request, reduces stock count and adds the order to the DB
     * @param shoppingCart cart of items to purchase 
     * @param purchaser user to purchase the items
     * @param purchaserCard card to pay for the items with
     * @return true if successful
     */
    public boolean purchaseItems(ShoppingCart shoppingCart, User purchaser, Card purchaserCard);
        
    /**
     * Get a new item by name
     * @param name of the item to get 
     * @return the item found 
     */
    public ShoppingItem getNewItemByName(String name);

}
