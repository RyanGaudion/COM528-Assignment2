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
import org.solent.com504.oodd.cart.model.dto.OrderItem;

/**
 *
 * @author cgallen
 */
public interface ShoppingCart {

    /**
     * Gets all items from the cart
     * @return a list of all items
     */
    public List<OrderItem> getShoppingCartItems();
    
    /**
     * Adds a specific item to the cart
     * @param shoppingItem item to add 
     */
    public void addItemToCart(ShoppingItem shoppingItem);
    
    /**
     * Removes a specific item from the cart
     * @param itemUuid UUID of the item to remove
     */
    public void removeItemFromCart(String itemUuid);

    /**
     * Empties the content of the cart
     */
    public void clearCart();
    
    /**
     * Gets the total cost of the cart
     * @return total price as double
     */
    public double getTotal();
    
}
