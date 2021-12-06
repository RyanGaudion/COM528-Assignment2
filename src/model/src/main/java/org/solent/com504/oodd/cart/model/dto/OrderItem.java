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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * An order item is a link between an order (invoice) and a SHopping item - 
 * allowing us to set the quantity ordered seperate to 
 * the shopping item stock count
 * @author rgaud
 */
@Entity
public class OrderItem {
    
    /**
     * Empty constructor
     */
    public OrderItem(){
        
    }
    
    /**
     *
     * @param item
     * @param quantity
     */
    public OrderItem(ShoppingItem item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }
    
    private ShoppingItem item;

    private int quantity;
    
    private Long id;
        
    /**
     * Gets the unique ID of the order item
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique ID of the order item
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }
        
    /**
     * Gets the item ordered
     * @return
     */
    @OneToOne
    public ShoppingItem getItem() {
        return item;
    }

    /**
     * Sets the item ordered
     * @param item
     */
    public void setItem(ShoppingItem item) {
        this.item = item;
    }

    /**
     * Gets the quantity ordered
     * @return
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity ordered
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
