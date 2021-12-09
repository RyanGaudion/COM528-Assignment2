/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.model.dto;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rgaud
 */
public class AddressTest {
    
    public AddressTest() {
    }

    /**
     * Test of getHouseNumber method, of class Address.
     */
    @Test
    public void testGetHouseNumber() {
        System.out.println("getHouseNumber");
        Address instance = new Address();
        instance.setHouseNumber("21");
        String expResult = "21";
        String result = instance.getHouseNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of setHouseNumber method, of class Address.
     */
    @Test
    public void testSetHouseNumber() {
        System.out.println("setHouseNumber");
        String houseNumber = "123q";
        Address instance = new Address();
        instance.setHouseNumber(houseNumber);
    }

    /**
     * Test of getAddressLine1 method, of class Address.
     */
    @Test
    public void testGetAddressLine1() {
        System.out.println("getAddressLine1");
        Address instance = new Address();
        instance.setAddressLine1("really long address 123");
        String expResult = "really long address 123";
        String result = instance.getAddressLine1();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAddressLine1 method, of class Address.
     */
    @Test
    public void testSetAddressLine1() {
        System.out.println("setAddressLine1");
        String addressLine1 = "super long address";
        Address instance = new Address();
        instance.setAddressLine1(addressLine1);
    }

    /**
     * Test of getAddressLine2 method, of class Address.
     */
    @Test
    public void testGetAddressLine2() {
        System.out.println("getAddressLine2");
        Address instance = new Address();
        instance.setAddressLine2("addressline2");
        String expResult = "addressline2";
        String result = instance.getAddressLine2();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAddressLine2 method, of class Address.
     */
    @Test
    public void testSetAddressLine2() {
        System.out.println("setAddressLine2");
        String addressLine2 = "hello line 2";
        Address instance = new Address();
        instance.setAddressLine2(addressLine2);
    }

    /**
     * Test of getPostcode method, of class Address.
     */
    @Test
    public void testGetPostcode() {
        System.out.println("getPostcode");
        Address instance = new Address();
        instance.setPostcode("PO910QZ");
        String expResult = "PO910QZ";
        String result = instance.getPostcode();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPostcode method, of class Address.
     */
    @Test
    public void testSetPostcode() {
        System.out.println("setPostcode");
        String postcode = "456HES";
        Address instance = new Address();
        instance.setPostcode(postcode);
    }

    /**
     * Test of getMobile method, of class Address.
     */
    @Test
    public void testGetMobile() {
        System.out.println("getMobile");
        Address instance = new Address();
        instance.setMobile("07000000000");
        String expResult = "07000000000";
        String result = instance.getMobile();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMobile method, of class Address.
     */
    @Test
    public void testSetMobile() {
        System.out.println("setMobile");
        String mobile = "070000000123";
        Address instance = new Address();
        instance.setMobile(mobile);
    }

    /**
     * Test of getTelephone method, of class Address.
     */
    @Test
    public void testGetTelephone() {
        System.out.println("getTelephone");
        Address instance = new Address();
        instance.setTelephone("02300000000");
        String expResult = "02300000000";
        String result = instance.getTelephone();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTelephone method, of class Address.
     */
    @Test
    public void testSetTelephone() {
        System.out.println("setTelephone");
        String telephone = "02300000000";
        Address instance = new Address();
        instance.setTelephone(telephone);
    }

    /**
     * Test of getCountry method, of class Address.
     */
    @Test
    public void testGetCountry() {
        System.out.println("getCountry");
        Address instance = new Address();
        instance.setCountry("United Kingdom");
        String expResult = "United Kingdom";
        String result = instance.getCountry();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCountry method, of class Address.
     */
    @Test
    public void testSetCountry() {
        System.out.println("setCountry");
        String country = "China";
        Address instance = new Address();
        instance.setCountry(country);
    }

    /**
     * Test of getCounty method, of class Address.
     */
    @Test
    public void testGetCounty() {
        System.out.println("getCounty");
        Address instance = new Address();
        instance.setCounty("Hampshire");
        String expResult = "Hampshire";
        String result = instance.getCounty();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCounty method, of class Address.
     */
    @Test
    public void testSetCounty() {
        System.out.println("setCounty");
        String county = "Coventry";
        Address instance = new Address();
        instance.setCounty(county);
    }

    /**
     * Test of getCity method, of class Address.
     */
    @Test
    public void testGetCity() {
        System.out.println("getCity");
        Address instance = new Address();
        instance.setCity("Southampton");
        String expResult = "Southampton";
        String result = instance.getCity();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCity method, of class Address.
     */
    @Test
    public void testSetCity() {
        System.out.println("setCity");
        String city = "London";
        Address instance = new Address();
        instance.setCity(city);
    }    
}
