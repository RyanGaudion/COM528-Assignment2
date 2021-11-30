/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.model.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author rgaud
 */
@Entity
public class OrderItem {
    
    public OrderItem(){
        
    }
    
    public OrderItem(ShoppingItem item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }
    
    private ShoppingItem item;

    private int quantity;
    
    private Long id;
        
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
        
    @OneToOne
    public ShoppingItem getItem() {
        return item;
    }

    public void setItem(ShoppingItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
