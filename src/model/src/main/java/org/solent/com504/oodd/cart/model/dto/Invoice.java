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

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author rgaud
 */
@Entity
public class Invoice {

    private Long id;

    private String invoiceNumber;

    private Date dateOfPurchase;

    private Double amountDue;
    
    private InvoiceStatus invoiceStatus;

    private List<OrderItem> purchasedItems;

    private User purchaser;

    /**
     *
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Unique ID of the invoice
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * get the state the invoice is in
     * @return
     */
    public InvoiceStatus getInvoiceStatus(){
        return invoiceStatus;
    }
    
    /**
     * set the state of the invoice
     * @param status
     */
    public void setInvoiceStatus(InvoiceStatus status){
        this.invoiceStatus = status;
    }
    
    /**
     * get the invoice's number
     * @return
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    /**
     * set the invoice's number
     * @param invoiceNumber
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    /**
     * gets the date of the invoice purchase 
     * @return
     */
    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    /**
     * sets the date of the invoice purchase
     * @param dateOfPurchase
     */
    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    /**
     * gets the total cost of the invoice
     * @return
     */
    public Double getAmountDue() {
        return amountDue;
    }

    /**
     * sets the total cost of the invoice
     * @param amountDue
     */
    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    /**
     * gets a list of all purchased items 
     * @return
     */
    @OneToMany(cascade = CascadeType.PERSIST) 
    public List<OrderItem> getPurchasedItems() {
        return purchasedItems;
    }

    /**
     * sets the list of items purchased
     * @param purchasedItems
     */
    public void setPurchasedItems(List<OrderItem> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    /**
     * gets the user that ordered the items
     * @return
     */
    @OneToOne
    public User getPurchaser() {
        return purchaser;
    }

    /**
     * sets the user that ordered the items 
     * @param purchaser
     */
    public void setPurchaser(User purchaser) {
        this.purchaser = purchaser;
    }

}
