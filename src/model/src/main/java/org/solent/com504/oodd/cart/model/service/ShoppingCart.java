/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public List<OrderItem> getShoppingCartItems();
    
    public void addItemToCart(ShoppingItem shoppingItem);
    
    public void removeItemFromCart(String itemUuid);
    
    public double getTotal();
    
}
