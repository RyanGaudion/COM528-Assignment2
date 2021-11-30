/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.bank;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A combination of a transaction Request as well as a transaction response
 * @author rgaud
 * @author kpeacock - suggested 'isRefunded' solution
 */
public class Transaction {
    private TransactionRequest transactionRequest;
    private TransactionResponse transactionResponse;
    private Boolean isRefund = false;
    final static Logger logger = LogManager.getLogger(Transaction.class);
    
    public Transaction(){
        
    }
    
    /**
     * Constructor requiring a transaction request and response
     * @param req the Transaction Request that was sent to the API
     * @param response the Transaction Response that was returned from the API
     */
    public Transaction(TransactionRequest req, TransactionResponse response){
        this.transactionRequest = req;
        this.transactionResponse = response;
    }
    
    /**
     * Get the transaction request
     * @return the transaction request
     */
    public TransactionRequest getTransactionRequest(){
        return transactionRequest;
    }
    
    /**
     * Get the transaction response
     * @return the transaction response
     */
    public TransactionResponse getTransactionResponse(){
        return transactionResponse;
    }

    /**
     * Gets if this transaction is a refund or has been refunded
     * @return true if this transaction is a refund or has been refunded
     */
    public Boolean getIsRefund(){
        return isRefund;
    }
    
    /**
     * Sets the transaction request if it's not null
     * @return true if the request was not null
     */
    public boolean setTransactionRequest(TransactionRequest request){
        logger.info(Transaction.class + "Set transaction request");
        if(request != null){
            transactionRequest = request;
            return true;
        }
        return false;
    }
    
    /**
     * Sets the transaction response if it's not null
     * @return true if the response was not null
     */
    public boolean setTransactionResponse(TransactionResponse response){
        logger.info(Transaction.class + "Set transaction response");
        if(response != null){
            transactionResponse = response;
            return true;
        }
        return false;
    }
    
    /**
     * Sets if this transaction is a refund or has been refunded
     * @return true if the value has been set successfully
     */
    public Boolean setIsRefund(Boolean refunded){
        isRefund = refunded;
        return true;
    }
}