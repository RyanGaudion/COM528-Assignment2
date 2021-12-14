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

import org.junit.Test;
import static org.junit.Assert.*;
import org.solent.com504.oodd.bank.Card;

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
    
    /**
     * Tests all card related gets and setters
     */
    @Test
    public void testCard(){
        Card userCard = new Card();
        userCard.setCVV("123");
        userCard.setCardnumber("0000000000000000");
        userCard.setEndDate("01/24");
        userCard.setIssueNumber("01");
        userCard.setName("Ryan");
        
        User user = new User();
        user.setSavedCard(userCard);
        
        assertEquals("", user.getSavedCard().getCVV());        
        assertEquals("0000000000000000", user.getSavedCard().getCardnumber());        
        assertEquals("01/24", user.getSavedCard().getEndDate());
        assertEquals("01", user.getSavedCard().getIssueNumber());        
        assertEquals("Ryan", user.getSavedCard().getName());

    }
    
}
