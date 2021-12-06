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
import org.junit.Test;

/**
 * Tests of the TransactionRequest class
 * @author rgaud
 */
public class TransactionRequestTest {
    /**
     * Tests the setting and getting of all properties on the transaction request
     */
    @Test
    public void RequestFullTest(){
        Card card = new Card();
        assertEquals(true, card.setCardnumber("0000 0000 0000 0000"));        
        assertEquals(true, card.setCVV("343"));

        Card card2 = new Card();
        assertEquals(true, card2.setCardnumber("0002 0002 0002 0002"));        
        assertEquals(true, card2.setCVV("3334"));
        TransactionRequest req = new TransactionRequest(card, card2, 100.00);
        
        assertEquals(100.00, req.getAmount(), 0.0);
        assertEquals(card2, req.getToCard());        
        assertEquals(card, req.getFromCard());
        assertEquals("3334", req.getToCard().getCVV());        
        assertEquals("343", req.getFromCard().getCVV());
        assertEquals("0000000000000000", req.getFromCard().getCardnumber());        
        assertEquals("0002000200020002", req.getToCard().getCardnumber()); 
    }
    
    /**
     * Tests the setting and getting of the from Card in more detail
     */
    @Test
    public void FromCardTest(){
        Card card = new Card();
        assertEquals(true, card.setCardnumber("0000 0000 0000 0000"));        
        assertEquals(true, card.setCVV("343"));
        
        TransactionRequest req = new TransactionRequest(card, new Card(), 0.0);
        assertEquals("0000000000000000", req.getFromCard().getCardnumber());
        assertEquals("343", req.getFromCard().getCVV());
    }
    
    /**
     * Tests the setting and getting of the to card in more detail
     */
    @Test
    public void ToCardTest(){
        Card card = new Card();
        assertEquals(true, card.setCardnumber("0002 0020 0002 0020"));        
        assertEquals(true, card.setCVV("2243"));
        
        TransactionRequest req = new TransactionRequest(new Card(), card, 0.0);
        assertEquals("0002002000020020", req.getToCard().getCardnumber());        
        assertEquals("2243", req.getToCard().getCVV());
    }
    
    /**
     * Tests the setting and getting of the transaction amount in mroe detail
     */
    @Test
    public void AmountTest(){
        TransactionRequest req = new TransactionRequest(new Card(), new Card(), 100.0);
        assertEquals(100.0, req.getAmount(), 0.0);
    }
}

