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
package org.solent.com504.oodd.bank;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test Methods for the card class
 * @author rgaud
 */
public class CardTest {   
    /**
     * Tests the setting and getting of a card number
     */
    @Test
    public void setBasicCardNumberTest()
    {
        Card card = new Card();
        assertEquals(true, card.setCardnumber("0000 0000 0000 0000"));
        assertEquals("0000000000000000", card.getCardnumber());
    }
    
    /**
     * Tests the setting and getting of a card name
     */
    @Test
    public void setNameTest()
    {
        Card card = new Card();
        assertEquals(true, card.setName("Joe Bloggs"));
        assertEquals("Joe Bloggs", card.getName());
    }
    
    /**
     * Tests the setting and getting of an invaliud Card Number (should return false)
     */
    @Test
    public void setInvalidCardNumberTest()
    {
        Card card = new Card();
        assertEquals(false, card.setCardnumber("000000000000000"));
        assertEquals("", card.getCardnumber());
    }
    
    /**
     * Tests the setting and getting of a Card CVV Number
     */
    @Test
    public void setCVVTest()
    {
        Card card = new Card();
        assertEquals(true, card.setCVV("846"));
        assertEquals("846", card.getCVV());
    }
    
    /**
     * Tests the clearing of the Card CVV number
     */
    @Test
    public void clearCVVTest()
    {
        Card card = new Card();
        assertEquals(true, card.setCVV("846"));
        assertEquals("846", card.getCVV());   
        card.clearCVV();
        assertEquals("", card.getCVV());

    }
    
    /**
     * Tests the setting and getting of a Card Issue Number
     */
    @Test
    public void setIssueNumberTest()
    {
        Card card = new Card();
        assertEquals(true, card.setIssueNumber("02"));
        assertEquals("02", card.getIssueNumber());
    }
    
    /**
     * Tests the setting and getting of a 4 digit Card CVV Number
     */
    @Test
    public void setCVV4DigitTest()
    {
        Card card = new Card();
        assertEquals(true, card.setCVV("8461"));
        assertEquals("8461", card.getCVV());
    }
    
    /**
     * Tests the setting and getting of an invalid length CVV Number
     */
    @Test
    public void setInvalidCVVTest()
    {
        Card card = new Card();
        assertEquals(false, card.setCVV("84615"));
        assertEquals("", card.getCVV());
    }
    
    /**
     * Tests the setting and getting of a card expiry date
     */
    @Test
    public void setExpiryDateTest()
    {
        Card card = new Card();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/yy"));              
        assertEquals(true, card.setEndDate(currentDate));
        assertEquals(currentDate, card.getEndDate());
        
        String futureDate = "05/60";
        assertEquals(true, card.setEndDate(futureDate));
        assertEquals(futureDate, card.getEndDate());
        
    }
    
    /**
     * Tests the setting and getting of multiple invalid expiry dates
     */
    @Test
    public void setInvalidExpiryDate(){
        Card card = new Card();
        assertEquals(false, card.setEndDate("abcde123"));        
        assertEquals(false, card.setEndDate("14/-0"));        
        assertEquals(false, card.setEndDate("14/24"));        
        assertEquals(false, card.setEndDate("01/01")); // Expired Card  
        assertEquals(true, card.setEndDate("11/24"));
    }
    
}