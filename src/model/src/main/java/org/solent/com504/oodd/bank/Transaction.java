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
    
    /**
     * Empty constructor for transaction
     */
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
     * @param request
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
     * @param response
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
     * @param refunded
     * @return true if the value has been set successfully
     */
    public Boolean setIsRefund(Boolean refunded){
        isRefund = refunded;
        return true;
    }
}