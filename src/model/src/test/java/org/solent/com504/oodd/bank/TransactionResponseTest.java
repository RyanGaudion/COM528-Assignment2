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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests for the transaction response class
 * @author rgaud
 */
public class TransactionResponseTest {

    /**
     * Tests the setting and getting of the response code
     */
    @Test
    public void ResponseCodeTest(){
        TransactionResponse response = new TransactionResponse();
        assertEquals(true, response.setCode(200));
        assertEquals(200, response.getCode());
    }
    
    /**
     * Tests the setting and getting of the from card number
     */
    @Test
    public void ResponseFromCardNoTest(){
        TransactionResponse response = new TransactionResponse();
        assertEquals(true, response.setFromCardNo("2000000000000000"));
        assertEquals("2000000000000000", response.getFromCardNo());
    }
    
    /**
     * Tests the setting and getting of the to card number
     */
    @Test
    public void ResponseToCardNoTest(){
        TransactionResponse response = new TransactionResponse();
        assertEquals(true, response.setToCardNo("4000000000000000"));
        assertEquals("4000000000000000", response.getToCardNo());
    }
    
    /**
     * Tests the setting and getting of the resposne message
     */
    @Test
    public void ResponseMessageTest(){
        TransactionResponse response = new TransactionResponse();
        assertTrue(response.setMessage("Invalid Card Name Selected"));
        assertEquals("Invalid Card Name Selected", response.getMessage());
    }
    
    /**
     * Tests the setting and getting of the response code
     */
    @Test
    public void ResponseStatus(){
        TransactionResponse response = new TransactionResponse();
        assertTrue(response.setStatus("SUCCESS"));
        assertEquals("SUCCESS", response.getStatus());
        
        assertTrue(response.setStatus("FAIL"));
        assertEquals("FAIL", response.getStatus());
    }
    
    /**
     * Tests the setting and getting of the response date
     */
    @Test
    public void ResponseDate(){
        TransactionResponse response = new TransactionResponse();
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
        Date date;
        try {
            date = parser.parse("2021-05-24-08-14");
            assertTrue(response.setTransactionDate(date));
            assertEquals("2021-05-24-08-14", parser.format(response.getTransactionDate()));
        } catch (ParseException ex) {
            Assert.fail("string formatter failed");
        }
        
    }
    
    /**
     * Tests the setting and getting of the response ID
     */
    @Test
    public void ResponseId(){
        TransactionResponse response = new TransactionResponse();
        assertTrue(response.setTransactionId("abcdefg1234"));
        assertEquals("abcdefg1234", response.getTransactionId());  
    }
    
}
