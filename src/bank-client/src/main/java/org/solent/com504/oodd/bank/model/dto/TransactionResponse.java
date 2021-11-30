/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.bank.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author rgaud
 * This model is the Data Transfer Object used as the response from the Bank API
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionResponse {
    private int code;
    private String message;
    private String status;
    private String fromCardNo;
    private String toCardNo;
    private String transactionId; //Could be a GUID
    private Date transactionDate;

    final static Logger logger = LogManager.getLogger(TransactionResponse.class);
    
    public TransactionResponse(){
        
    }
    
    //Get Methods

    /**
     * Gets the status code returned from the API
     * @return int representation of the status code
     */
    public int getCode(){
        return code;
    }

    /**
     * Gets the message property
     * @return the message returned from the Web API
     */
    public String getMessage(){
        return message;
    }

    /**
     * Gets the status of the request (SUCCESS or FAILURE)
     * @return TransactionStatus enum to represent success or failure
     */
    public String getStatus(){
        return status;
    }

    /**
     * Gets the from card number
     * @return Card number that the transaction was from
     */
    public String getFromCardNo(){
        return fromCardNo;
    }

    /**
     * Gets the to card number
     * @return Card number of the card that the money was sent to
     */
    public String getToCardNo(){
        return toCardNo;
    }

    /**
     * Gets the transaction unique ID
     * @return Unique Transaction ID as a String 
     */
    public String getTransactionId(){
        return transactionId;
    }

    /**
     * Gets the Date of the transaction
     * @return Date and Time that the transaction response was received
     */
    public Date getTransactionDate(){
        return transactionDate;
    }
    
    //Set Methods
    /**
     * Sets the status code property
     * @param code the int to set the code property to
     * @return True if the Length of the code is 3, if not doesn't set property and returns false
     */
    public Boolean setCode(int code){
        //Check Code is 3 digits
        if(String.valueOf(code).length() == 3){
            logger.debug(TransactionResponse.class + "Set Code Validation - Success: " + code);
            this.code = code;
            return true;
        }
        logger.debug(TransactionResponse.class + "Set Code Validation - Failed: " + code);
        return false;
    }
    
    /**
     * Sets the message property of the response
     * @param message The message from the API
     * @return True unless there was an issue setting the property
     */
    public Boolean setMessage(String message){
        this.message = message;
        return true;
    }

    /**
     * Sets the status of the Transaction Response
     * @param status The Status of the Request
     * @return True unless there was an issue setting the property
     */
    public Boolean setStatus(String status){
        this.status = status;
        return true;
    }

    /**
     * Sets the card number of the From Card
     * @param fromCardNo the Card number of the from card
     * @return True unless there was an issue setting the property
     */
    public Boolean setFromCardNo(String fromCardNo){
        this.fromCardNo = fromCardNo;
        return true;
    }

    /**
     * Sets the card number of the To Card
     * @param toCardNo the Card number of the to card
     * @return True unless there was an issue setting the property
     */
    public Boolean setToCardNo(String toCardNo){
        this.toCardNo = toCardNo;
        return true;
    }

    /**
     * Sets the unique transaction ID of the request
    * @param transactionId the unique ID for the transaction 
    * @return True unless there was an issue setting the property
    */
    public Boolean setTransactionId(String transactionId){
        this.transactionId = transactionId;
        return true;
    }

    /**
     * Sets the Date of the transaction
     * @param date the date and time of the request
     * @return True unless there was an issue setting the property
     */
    public Boolean setTransactionDate(Date date){
        this.transactionDate = date;
        return true;
    } 
    
    
    /**
     * Returns a custom JSON representation of the TransactionResponse Object
     */
    @Override
    public String toString() {
        return "TransactionResponse{" + "transactionId="+ transactionId + ", transactionDate="+ transactionDate.toString() +", message=" + message + ", status=" + status + '}';
    }
}
