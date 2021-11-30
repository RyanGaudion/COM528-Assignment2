/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.model.service;

import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import java.util.List;
import org.solent.com504.oodd.cart.model.dto.User;

/**
 *
 * @author cgallen
 */
public interface ShoppingService {
    
        public List<ShoppingItem> getAvailableItems();        
        public List<ShoppingItem> searchAvailableItems(String searchQuery);

        
        public boolean purchaseItems(ShoppingCart shoppingCart, User purchaser);
        
        public ShoppingItem getNewItemByName(String uuid);

}
