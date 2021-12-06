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

import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents the different card companies that can be detected.
 * Sourced from: https://stackoverflow.com/a/23814692
 * @author rgaudion
 */
public enum CardCompany {

    UNKNOWN,
    VISA("^4[0-9]{12}(?:[0-9]{3}){0,2}$"),
    MASTERCARD("^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$"),
    AMERICAN_EXPRESS("^3[47][0-9]{13}$"),
    DINERS_CLUB("^3(?:0[0-5]\\d|095|6\\d{0,2}|[89]\\d{2})\\d{12,15}$"),
    DISCOVER("^6(?:011|[45][0-9]{2})[0-9]{12}$"),
    JCB("^(?:2131|1800|35\\d{3})\\d{11}$"),
    CHINA_UNION_PAY("^62[0-9]{14,17}$");

    /**
     * Gets logger for card company class
     */
    public static Logger logger = LogManager.getLogger(CardCompany.class);
    private Pattern pattern;

    CardCompany() {
        this.pattern = null;
    }

    /**
     * 
     * @param pattern The expression to be compiled.
     */
    CardCompany(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    /**
     * Detects which card company issued the card number.
     * @param cardNumber the Card Number to detect the card company of
     * @return CardCompany associated with the input number. Returns CardComapny.UNKNOWN for non-matches.
     */
    public static CardCompany detect(String cardNumber) {
        if (cardNumber == null) {
            logger.warn(CardCompany.class + ": Null card entered.");
            return CardCompany.UNKNOWN;
        }
        cardNumber = cardNumber.replaceAll("[^\\d]", ""); //("[^0-9]+$", ""); // Remove all non-numerics.
        
        for (CardCompany cardType : CardCompany.values()) {
            if (null == cardType.pattern) {
                continue;
            }
            if (cardType.pattern.matcher(cardNumber).matches()) {
                return cardType;
            }
        }
        return UNKNOWN;
    }
}
