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
package org.solent.com504.oodd.cart.model.service;

import java.util.List;
import org.solent.com504.oodd.bank.Card;
import org.solent.com504.oodd.bank.Transaction;

/**
 * Interface for the Banking Service abstraction
 * @author rgaud
 */
public interface IBankingService {    

    /**
     * Send money from the from card to the to card of the specified amount. To Card is always the shop keeper's card
     * @param fromCard the card to send money from
     * @param amount the amount to send
     * @return Transaction model which includes both the transaction request and reponse
     */
    public Transaction sendTransaction(Card fromCard, Double amount);

    //Automatically creates a request with the negative number of the transaction.getresponse.getamount
    //Also checks to see if the transaction to refund was successful in the first place before trying to refund it

    /**
     * Refund a completed transaction
     * @param transaction the transaction to refund
     * @return a new transaction model for the refunded transaction
     */
    public Transaction refundTransaction(Transaction transaction);
    
    /**
     * Send a transaction from the shopkeepers Card
     * @param toCard card to send the amount to
     * @param amount how much money to send to the card
     * @return Transaction showing the completed refund
     */
    public Transaction refundSimpleTransaction(Card toCard, Double amount);
            
    /**
     * Gets the last recent transactions
     * @return A list of recent transactions
     */
    List<Transaction> getLatestSuccessfulTransactions();
    
    /**
     * Removes all transactions from the list of previous transactions
     * @return true if clear was successful
     */
    public Boolean clearTransactions();

}
