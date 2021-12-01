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
package org.solent.com504.oodd.cardchecker;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class providing details of a card validation.
 * @author rgaudion
 */
public class CardValidationResult {
    private String message;
    private Boolean isValid;
    private CardCompany cardCompany;
    final static Logger logger = LogManager.getLogger(CardValidationResult.class);
    
    /**
     * Constructor to use for unknown card companies results.
     * @param isValid true is the card is valid or false if it is not
     * @param message if the card is not valid then the message will give a reason
     */
    public CardValidationResult(Boolean isValid, String message) {
        logger.info("Creating card validation result.");
        this.cardCompany = CardCompany.UNKNOWN;
        this.message = message;
        this.isValid = isValid;
    }
    
    /**
     * Constructor to use for valid results.
     * @param message reason why the card is or is not valid
     * @param cardCompany company that the card relates to
     */
    public CardValidationResult(String message, CardCompany cardCompany) {
        logger.info("Creating valid card validation result.");
        this.isValid = true;
        this.message = message;
        this.cardCompany = cardCompany;
    }
    
    /**
     * Gets this card's card company.
     * @return The Card company of this card.
     */
    public CardCompany getCardCompany() {
        return this.cardCompany;
    }
    
    /**
     * 
     * @return This result's message.
     */
    public String getMessage() {
        return this.message;
    }
    
    /**
     * 
     * @return This result's validity.
     */
    public Boolean getIsValid(){
        return this.isValid;
    }     
}
