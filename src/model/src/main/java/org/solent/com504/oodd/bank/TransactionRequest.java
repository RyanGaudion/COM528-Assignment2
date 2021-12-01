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

import org.solent.com504.oodd.bank.Card;
import java.text.NumberFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A Data Transfer Object used to send a transaction request to the Bank API 
 * @author rgaud
 */
public class TransactionRequest {
    private Card fromCard;
    private Card toCard;
    private Double amount;

    final static Logger logger = LogManager.getLogger(TransactionRequest.class);

    /**
     * Empty constructor - required for serialising and de-serialising
     */
    public TransactionRequest(){
        logger.info("New Transaction Request - empty constructor");
    }
    
    /**
     * Quick Constructor which sets or necessary properties
     * @param from fromCard for the transaction
     * @param to toCard for the transaction
     * @param amount amount for the transaction as a double
     */
    public TransactionRequest(Card from, Card to, Double amount){
        logger.info("New Transaction Request - full constructor");
        fromCard = from;
        toCard = to;
        this.amount = amount;
    }
    
    /**
     * Sets the from Card
     * @param card Card that the transaction should be made from
     * @return returns True as long as there were no errors in setting the property
     */
    public boolean setFromCard(Card card){
        this.fromCard = card;
        return true;
    }
    
    /**
     * Sets the to Card
     * @param card Card that the transaction amount should be sent to
     * @return returns True as long as there were no errors in setting the property
     */
    public boolean setToCard(Card card){
        this.toCard = card;
        return true;
    }
    
    /**
     * Sets how much money should be transferred for the transaction
     * @param amount the amount to transfer (as a Double)
     * @return returns True as long as there were no errors in setting the property
     */
    public boolean setAmount(double amount){
        this.amount = amount;
        return true;
    }
    
    /**
     * Simply gets the from Card for the transactionRequest
     * @return Card that the Transaction amount will be taken from
     */
    public Card getFromCard(){
        return fromCard;
    }

    /**
     * Simply gets the to Card for the transactionRequest
     * @return Card that the Transaction amount will be sent to
     */
    public Card getToCard(){
        return toCard;
    }

    /**
     * Simply gets the amount set for the transactionRequest
     * @return Double representation of the amount that will be transferred from the "fromCard" to the "toCard"
     */
    public Double getAmount(){
        return amount;
    }

    /**
     * Returns a custom JSON representation of the TransactionRequest Object
     */
    @Override
    public String toString() {
        NumberFormat fmt = NumberFormat.getInstance(); 
        fmt.setGroupingUsed(false); 
        fmt.setMaximumIntegerDigits(999); 
        fmt.setMaximumFractionDigits(999); 
        String total2 = fmt.format(amount);
        return "TransactionRequest{" + "fromCard=" + fromCard + ", toCard=" + toCard + ", amount=" + total2 + '}';
    }
}
