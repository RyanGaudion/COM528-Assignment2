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
package org.solent.com504.oodd.bank.client.test.manual;

import org.solent.com504.oodd.spring.ClientTestConfiguration;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.solent.com504.oodd.bank.client.impl.BankRestClient;
import org.solent.com504.oodd.bank.Card;
import org.solent.com504.oodd.bank.TransactionRequest;
import org.solent.com504.oodd.bank.TransactionResponse;
import org.solent.com504.oodd.bank.model.client.IBankRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
/**
 * 
 * @author rgaudion
 * This Class is responsible for testing the transaction methods of the REST Client
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ClientTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class BankClientTests {

    final static Logger logger = LogManager.getLogger(BankClientTests.class);

    @Autowired
    IBankRestClient client;
    
    String bankUrl = "http://com528bank.ukwest.cloudapp.azure.com:8080/rest";
    Card fromCard = null;
    Card toCard = null;
    
    String toUsername=null;
    String toPassword=null;

    /*
     * Before the Tests are performed a default fromCard, toCard
     * as well as Username and password are setup
     */

    /**
     *
     */

    @Before
    public void before() {
        fromCard = new Card();
        fromCard.setCardnumber("5133880000000012");
        fromCard.setCVV("123");
        fromCard.setIssueNumber("01");
        fromCard.setEndDate("11/21");
        fromCard.setName("test user1");

        toCard = new Card();
        toCard.setCardnumber("4285860000000021");
        toCard.setCVV("123");
        toCard.setEndDate("11/21");
        toCard.setName("test user2");
        
        toUsername = "testuser2";
        toPassword = "defaulttestpass";
    }

    /**
     * Performs a valid transaction without authentication
     */
    @Test
    public void testClient() {

        Double amount = 0.0;

        TransactionRequest req = new TransactionRequest(fromCard, toCard, amount);
        TransactionResponse response = client.transferMoney(req);
        logger.debug("transaction reply:" + response);

        logger.debug(response);       
        assertEquals("SUCCESS", response.getStatus());


    }

    /**
     * Performs a valid transaction using authentication of username and password
     */
    @Test
    public void testClientAuth() {
        Double amount = 0.0;

        // testing with auth
 
        TransactionRequest req = new TransactionRequest(fromCard, toCard, amount);
        TransactionResponse response = client.transferMoney(req, toUsername, toPassword);
        logger.debug("transaction with auth reply:" + response);
        
        assertEquals("SUCCESS", response.getStatus());

    }
    
    /**
     * Performs a transaction between an invalid from card and a valid to card, which should fail.
     */
    @Test
    public void invalidFromCardTest(){
        Card invalidFromCard = new Card();
        invalidFromCard.setName("Invalid From Card");
        invalidFromCard.setCVV("989");
        invalidFromCard.setCardnumber("invalid");
        invalidFromCard.setEndDate("23/43");

        TransactionRequest req = new TransactionRequest(invalidFromCard, toCard, 50.0);
        
        TransactionResponse response =  client.transferMoney(req);

        assertEquals ("FAIL", response.getStatus());
    }
    
    /**
     * Performs a transaction between a valid from card and an invalid to card, which should fail.
     */
    @Test
    public void invalidToCardTest(){
        Card invalidToCard = new Card();
        invalidToCard.setName("Invalid To Card");
        invalidToCard.setCVV("989");
        invalidToCard.setCardnumber("invalid");
        invalidToCard.setEndDate("11/22");
        
        TransactionRequest req = new TransactionRequest(fromCard, invalidToCard, 50.0);
        
        TransactionResponse response =  client.transferMoney(req);

        assertEquals ("FAIL", response.getStatus());
    }
    
    /**
     * Performs a transaction between 2 valid credit cards of an invalid amount (-50.0) , which should fail.
     */
    @Test
    public void InvalidTransactionAmountTest(){

        TransactionRequest req = new TransactionRequest(fromCard, toCard, 50000000000.0);
        TransactionResponse response =  client.transferMoney(req);

        assertEquals ("FAIL", response.getStatus());
    }

}
