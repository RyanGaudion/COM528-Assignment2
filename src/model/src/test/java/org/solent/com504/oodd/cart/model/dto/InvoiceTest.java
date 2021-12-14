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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rgaud
 */
public class InvoiceTest {
    

    /**
     * Test of getId method, of class Invoice.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Invoice instance = new Invoice();
        instance.setId(10L);
        Long expResult = 10L;
        Long result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of getInvoiceStatus method, of class Invoice.
     */
    @Test
    public void testGetInvoiceStatus() {
        System.out.println("getInvoiceStatus");
        Invoice instance = new Invoice();
        instance.setInvoiceStatus(InvoiceStatus.PROCESSING);
        InvoiceStatus expResult = InvoiceStatus.PROCESSING;
        InvoiceStatus result = instance.getInvoiceStatus();
        assertEquals(expResult, result);
    }

    /**
     * Test of getInvoiceNumber method, of class Invoice.
     */
    @Test
    public void testGetInvoiceNumber() {
        System.out.println("getInvoiceNumber");
        Invoice instance = new Invoice();
        instance.setInvoiceNumber("0101");
        String expResult = "0101";
        String result = instance.getInvoiceNumber();
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getRefunded method, of class Invoice.
     */
    @Test
    public void testGetRefunded() {
        System.out.println("getRefunded");
        Invoice instance = new Invoice();
        Boolean preExpResult = false;
        Boolean preResult = instance.getRefunded();
        assertEquals(preExpResult, preResult);
        instance.setRefunded(true);
        Boolean expResult = true;
        Boolean result = instance.getRefunded();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDateOfPurchase method, of class Invoice.
     */
    @Test
    public void testGetDateOfPurchase() {
        System.out.println("getDateOfPurchase");
        Date date = new Date();
        Invoice instance = new Invoice();
        instance.setDateOfPurchase(date);
        Date expResult = date;
        Date result = instance.getDateOfPurchase();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAmountDue method, of class Invoice.
     */
    @Test
    public void testGetAmountDue() {
        System.out.println("getAmountDue");
        Invoice instance = new Invoice();
        instance.setAmountDue(4.00);
        Double expResult = 4.00;
        Double result = instance.getAmountDue();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPurchasedItems method, of class Invoice.
     */
    @Test
    public void testGetPurchasedItems() {
        System.out.println("getPurchasedItems");
        List<OrderItem> items = new ArrayList<OrderItem>();
        OrderItem item = new OrderItem();
        item.setQuantity(4);
        items.add(item);
        Invoice instance = new Invoice();
        instance.setPurchasedItems(items);
        
        assertEquals(4, instance.getPurchasedItems().get(0).getQuantity());
    }

    /**
     * Test of getPurchaser method, of class Invoice.
     */
    @Test
    public void testGetPurchaser() {
        System.out.println("getPurchaser");
        User user = new User();
        user.setFirstName("Bob");
        Invoice instance = new Invoice();
        instance.setPurchaser(user);
        assertEquals("Bob", instance.getPurchaser().getFirstName());
    }
    
    /**
     * Test of getPurchaserCard method, of class Invoice.
     */
    @Test
    public void testGetPurchaserCard() {
        System.out.println("getPurchaserCard");
        Invoice instance = new Invoice();
        instance.setPurchaserCard("4000400040004000");
        assertEquals("4000400040004000", instance.getPurchaserCard());
    }

    
}
