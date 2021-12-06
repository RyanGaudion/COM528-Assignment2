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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.solent.com504.oodd.bank.Card;
import org.solent.com504.oodd.bank.Transaction;
import org.solent.com504.oodd.bank.TransactionRequest;
import org.solent.com504.oodd.bank.TransactionResponse;

/**
 * Tests for the transaction class
 * @author rgaud
 */
public class TransactionTest {
    
    /*
    * Tests the setting & Getting of transaction request
    */
    @Test
    public void RequestTest(){
        TransactionRequest request = new TransactionRequest();
        assertEquals(true, request.setAmount(4.00));
        
        Transaction transaction = new Transaction();
        assertEquals(true, transaction.setTransactionRequest(request));
        assertEquals(4.00, transaction.getTransactionRequest().getAmount(), 0);
    }
    
    /*
    * Tests the setting & Getting of transaction response
    */
    @Test
    public void ResponseTest(){
        TransactionResponse response = new TransactionResponse();
        assertEquals(true, response.setStatus("Success"));
        
        Transaction transaction = new Transaction();
        assertEquals(true, transaction.setTransactionResponse(response));
        assertEquals("Success", transaction.getTransactionResponse().getStatus());
    }
    
    /*
    * Tests the setting & Getting of transaction is refund
    */
    @Test
    public void IsRefundTest(){
        Transaction transaction = new Transaction();
        assertEquals(true, transaction.setIsRefund(true));
        assertTrue(transaction.getIsRefund());
        assertEquals(true, transaction.setIsRefund(false));
        assertFalse(transaction.getIsRefund());
    }
    
    /**
     * Full test of all getters and setters on the transaction object
     */
    @Test
    public void fullTransactionTest()
    {
        Card card = new Card();
        assertEquals(true, card.setCardnumber("0000 0000 0000 0000"));
        
        Card card2 = new Card();
        assertEquals(true, card2.setCardnumber("0002 0002 0002 0002"));
        
        
        TransactionRequest req = new TransactionRequest(card, card2, 100.00);
        
        TransactionResponse response = new TransactionResponse();
        assertEquals(true, response.setCode(300));
        assertEquals(true, response.setFromCardNo(card.getCardnumber()));
        assertEquals(true, response.setToCardNo(card2.getCardnumber()));
        
        Transaction transaction = new Transaction();
        assertEquals(true, transaction.setTransactionRequest(req));
        assertEquals(true, transaction.setTransactionResponse(response));  
        
        assertEquals(300, transaction.getTransactionResponse().getCode());
        assertEquals("0002000200020002", transaction.getTransactionRequest().getToCard().getCardnumber());
    }
    
}
