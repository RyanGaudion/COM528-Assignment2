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
package org.solent.com504.oodd.cart.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.bank.model.client.IBankRestClient;
import org.solent.com504.oodd.bank.Card;
import org.solent.com504.oodd.bank.Transaction;
import org.solent.com504.oodd.bank.TransactionRequest;
import org.solent.com504.oodd.bank.TransactionResponse;
import org.solent.com504.oodd.cart.model.service.IBankingService;
import org.solent.com504.oodd.properties.dao.impl.PropertiesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * The Banking Service responsible for keeping track of transactions as well as accessing the Bank Rest Client
 * @author rgaud
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BankingService implements IBankingService{

    private final String apiUsername;
    private final String apiPassword;    
    private final Card shopKeeperCard;

    private final List<Transaction> transactions = new ArrayList();
    
    @Autowired
    private IBankRestClient client;

    final static Logger logger = LogManager.getLogger(BankingService.class);    
    final static Logger TransactionLogger = LogManager.getLogger("Transaction_Logger");
    
    /**
     *
     * @param properties
     */
    public BankingService(PropertiesDao properties){
        apiUsername = properties.getProperty("org.solent.oodd.pos.service.apiUsername");
        apiPassword = properties.getProperty("org.solent.oodd.pos.service.apiPassword");
        shopKeeperCard = new Card();
        shopKeeperCard.setCardnumber(properties.getProperty("org.solent.oodd.pos.service.shopKeeperCard"));
    }
    
    
    /**
     * Send Transaction method simply sends money from 1 card to another using the Bank API. 
     * This method implements Http Authentication
     */
    @Override
    public Transaction sendTransaction(Card fromCard, Double amount) {
        try
        {
            logger.debug("Send Transaction from: " + fromCard.getCardnumber() + " to: " + shopKeeperCard.getCardnumber() + " for: " + amount);

            TransactionRequest request = new TransactionRequest(fromCard, shopKeeperCard, amount);

            TransactionResponse response = client.transferMoney(request, apiUsername, apiPassword);


            logger.debug("Transaction Response Status: " + response.getStatus());

            TransactionLogger.info("Sent Transaction: " + request.toString() + response.toString());

            Transaction transaction = new Transaction(request, response);
            transactions.add(transaction);
            return transaction;
        }
        catch(Exception ex){
            TransactionLogger.info("Transaction Failed: " + "From Card: " + fromCard.toString() + "To Card: " + shopKeeperCard.toString() +", amount:" + amount.toString());
            throw ex;
        }

    }

    /**
     * Refund Transaction takes in a full Transaction object and then fetches the 
     * required information to refund the transaction
     */
    @Override
    public Transaction refundTransaction(Transaction transaction) {
        try
        {
            logger.debug("Refund Transaction from: " + transaction.getTransactionRequest().getFromCard().getCardnumber() + " to: " + transaction.getTransactionRequest().getToCard().getCardnumber() + " for: " + transaction.getTransactionRequest().getAmount());

            Card fromCard = shopKeeperCard;       
            Card toCard = transaction.getTransactionRequest().getFromCard();
            Double amount = transaction.getTransactionRequest().getAmount();

            TransactionRequest request = new TransactionRequest(fromCard, toCard, amount);
            
            TransactionLogger.info("Transaction To Refund: " + transaction.getTransactionRequest().toString() + transaction.getTransactionResponse().toString());
            
            TransactionResponse response = client.transferMoney(request);
            logger.debug("Refund Response Status: " + response.getStatus());

            TransactionLogger.info("Refund Transaction: " + request.toString() + response.toString());

            Transaction refundTransaction = new Transaction(request, response);
            if(refundTransaction.getTransactionResponse().getStatus().equals("SUCCESS")){
                refundTransaction.setIsRefund(true);
                transaction.setIsRefund(true);
                transactions.add(refundTransaction);
            }
            return refundTransaction;
        }
        catch(Exception ex){
            TransactionLogger.info("Refund Failed - : " + transaction.getTransactionRequest().toString() + transaction.getTransactionResponse().toString());
            throw ex;
        }        
    }
    
    /**
     * This uses the transactions list to get the 9 most recent transactions
     */
    @Override
    public List<Transaction> getLatestSuccessfulTransactions(){
        //Returns either all the transactions or the last 9 - whichever is smallest
        logger.debug("Get Latest Transactions: " + transactions.size());
        List<Transaction> successfulTransactions = new ArrayList<Transaction>();
        for (Transaction transaction : transactions) {
            if(transaction.getTransactionResponse().getStatus() != null && transaction.getTransactionResponse().getStatus().equals("SUCCESS") && transaction.getIsRefund() != true){
                successfulTransactions.add(transaction);
            }
        }
        List<Transaction> latestTransactions = successfulTransactions.subList(successfulTransactions.size()- Math.min(successfulTransactions.size(), 9), successfulTransactions.size());
        return latestTransactions;
    }
    
    /**
     * Clears all transactions from the list of transactions
     */
    @Override
    public Boolean clearTransactions(){
        transactions.clear();
        return true;
    }
    
}
