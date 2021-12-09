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
public class UserTest {
    
    /**
     * Tests all setters and getters of user
     */
    @Test
    public void testSetGetValues() {
        User user = new User();
        Address add = new Address();
        add.setCity("London");
        
        user.setAddress(add);
        user.setEnabled(true);
        user.setFirstName("Bob");
        user.setId(10L);
        user.setPassword("SuperSecret");
        user.setHashedPassword("asdfghjkl");
        user.setSecondName("Smith");
        user.setUserRole(UserRole.ADMINISTRATOR);
        user.setUsername("bobsmith1");
        
        
        assertEquals("London", user.getAddress().getCity());
        assertEquals(true, user.getEnabled());
        assertEquals("Bob", user.getFirstName());
        assertEquals("SuperSecret", user.getPassword());
        assertEquals("asdfghjkl", user.getHashedPassword());
        assertEquals("10", user.getId().toString());
        assertEquals("Smith", user.getSecondName());
        assertEquals("ADMINISTRATOR", user.getUserRole().toString());
        assertEquals("bobsmith1", user.getUsername());
    }
    
}
