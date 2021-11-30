/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.model.service;

import java.util.List;
import org.solent.com504.oodd.bank.model.dto.Card;
import org.solent.com504.oodd.bank.model.dto.Transaction;

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
