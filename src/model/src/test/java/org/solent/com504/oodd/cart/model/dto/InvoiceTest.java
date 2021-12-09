/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    public InvoiceTest() {
    }

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

    
}
