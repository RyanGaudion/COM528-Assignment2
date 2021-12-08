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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author cgallen
 */
@Entity
public class ShoppingItem {
    
    private Long id;
    private String uuid=null;
    private String name=null;
    private Integer quantity=0;
    private Double price=0.0;
    private String filename;
    private Boolean deactivated = false;
    private String category=null;
    
    /**
     * Empty Item constructor
     */
    public ShoppingItem(){
        
    }

    /**
     *
     * @param name
     * @param price
     */
    public ShoppingItem(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Gets the unique ID of the Item
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the item
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the UUID of the item
     * @return
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the UUID of the item
     * @param uuuid
     */
    public void setUuid(String uuuid) {
        this.uuid = uuuid;
    }
    
    /**
     * Gets the image filename of the item
     * @return
     */
    public String getFilename(){
        return filename;
    }
    
    /**
     * Sets the image filename of the item
     * @param filename
     */
    public void setFilename(String filename){
        this.filename = filename;
    }
    
    /**
     * Gets whether the item is deactivated or not
     * @return 
     */
    public Boolean getDeactivated(){
        return deactivated;
    }
    
    /**
     * Sets whether the item is deactivated or not
     * @param deactivated 
     */
    public void setDeactivated(Boolean deactivated){
        this.deactivated = deactivated;
    }
    
    /**
     * Gets the category of the item
     * @return 
     */
    public String getCategory(){
        return category;
    }
    
    /**
     * Sets the category of the item
     * @param category 
     */
    public void setCategory(String category){
        this.category = category;
    }

    /**
     * Gets the name of the item
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the quantity in stock of the item
     * @return
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity in stock of the item
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price of the item
     * @return
     */
    public Double getPrice() {
        return price;
    }

    /** 
     * Sets the price of the item
     * @param price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ShoppingItem{" + "uuuid=" + uuid + ", name=" + name + ", quantity=" + quantity + ", price=" + price + '}';
    }
    
            
    
}
