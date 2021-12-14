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
package org.solent.com504.oodd.cart.service.test;

import java.util.List;
import javax.transaction.Transactional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.solent.com504.oodd.bank.Card;
import org.solent.com504.oodd.bank.Transaction;
import org.solent.com504.oodd.cart.service.BankingService;
import org.solent.com504.oodd.cart.service.spring.test.ServiceTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 *
 * @author rgaud
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class BankingServiceTest {
    
    @Autowired
    BankingService bankingService;
    
    /**
     * Tests sending a transaction using the banking service
     */
    @Test
    public void testTransaction() {        
        
        Card fromCard = new Card();
        fromCard.setCardnumber("5133880000000012");
        fromCard.setCVV("123");
        fromCard.setIssueNumber("01");
        fromCard.setEndDate("11/21");
        fromCard.setName("test user1");
                
        Transaction transaction = bankingService.sendTransaction(fromCard, 1.0);
        
        assertEquals("SUCCESS", transaction.getTransactionResponse().getStatus());
    }
    
    /**
     * Tests getting latest transactions from banking service
     */
    @Test
    public void testGetTransactions(){
        testTransaction();
        
        List<Transaction> transactions = bankingService.getLatestSuccessfulTransactions();
        
        assertTrue(transactions.size() > 0);
    }
    
    /**
     * Tests refunding with the banking service
     */
    @Test
    public void testRefund(){
        testGetTransactions();
        List<Transaction> transactions = bankingService.getLatestSuccessfulTransactions();
        assertTrue(transactions.size() > 0);
        Transaction refund = bankingService.refundTransaction(transactions.get(0));
        assertEquals("SUCCESS", refund.getTransactionResponse().getStatus());

    }
    
    /**
     * Tests clearing the banking service
     */
    @Test
    public void testClear(){
        testTransaction();
        
        List<Transaction> transactions = bankingService.getLatestSuccessfulTransactions();
        assertTrue(transactions.size() > 0);
        
        assertTrue(bankingService.clearTransactions());
        List<Transaction> transactions2 = bankingService.getLatestSuccessfulTransactions();

        assertTrue(transactions2.isEmpty());
    }
}
