/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cardchecker.test;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.solent.com504.oodd.cardchecker.CardCompany;
import org.solent.com504.oodd.cardchecker.CardValidationResult;
/**
 *
 * @author rgaudion
 */
public class CardCheckerTest {

    public static Logger logger = LogManager.getLogger(CardCheckerTest.class);

    /**
     * Removes non-numeric characters from the input, and 
     * checks the validity of the given card number.
     *
     * @param cardInput The Card Number to check the validity of
     * @return CardValidationResult informing on pass/fail of check as well as card company name.
     */
    public static CardValidationResult checkValidity(final String cardInput) {
        /**
         * Null values should not be accepted.
         */
        if (cardInput == null) {
            logger.warn(": Null card entered.");
            return new CardValidationResult(false, "Card cannot be null.");
        }

        /**
         * Cards are only valid between 13 & 19 digits.
         */
        String cardNumber = cardInput.replaceAll("[^\\d]", ""); //("[^0-9]+$", ""); // Remove all non-numerics.
        if (cardInput.length() < 13 || cardInput.length() > 19) {
            logger.warn(": Bad card length for " + cardInput);
            return new CardValidationResult(false, "Card length fell outside of appropriate range.");
        }

        if (!checkLuhn(cardNumber)) {
            logger.warn(": failed Luhn check.");
            return new CardValidationResult(false, cardNumber + ": Failed Luhn check");
        }
        
        CardCompany cc = CardCompany.detect(cardNumber);
        if (cc == CardCompany.UNKNOWN) {
            logger.warn(" CC Check, No." + cardNumber + ", cc: " + cc );
            return new CardValidationResult(false ,"Failed card company check.");
        }
        
        return new CardValidationResult("Card verified.", cc);
    }

    /**
     * Performs only the sum part of the Luhn calculation.
     * Can choose to ignore the last digit or take whole number.
     * @param cardNo
     * @param ignoreLastDigit
     * @return Int sum according the Luhn algorithm.
     */
    private static int sumDigits(String cardNo, boolean ignoreLastDigit) {
        int offset = 1; // To use the full number.
        if (ignoreLastDigit == true) {
            offset = 2;
        } // To skip check digit.

        // Perform checksum calc.
        int sum = 0;
        int parity = cardNo.length() % 2;
        for (int i = cardNo.length() - offset; i >= 0; i--) {
            int digit = Integer.parseInt(cardNo.substring(i, i + 1));

            if (i % 2 == parity) { // Is true for every other digit.
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        return sum;
    }

    /**
     * Performs the MOD10 portion of the Luhn calculation to
     * validate the check sum and check digit.
     * @param cardNo
     * @return True/False whether the card number passed the Luhn algorithm.
     */
    private static boolean checkLuhn(String cardNo) {
        logger.info(": Performing Luhn check.");
        if (cardNo == null || cardNo.length() == 0) {
            logger.warn(": Null card entered.");
            return false;
        } else if (!cardNo.matches("[0-9]+$")) {
            logger.warn(": Non-numeric card entered.");
            return false;
        }
        int sum = sumDigits(cardNo, false); // Take in whole card.
        // Don't perform mod10 when sum = 0.
        boolean sumCheck = (sum == 0) ? false : (sum % 10 == 0);

        // Ignore last digit of number when checking check digit.
        String digit = (10 - sumDigits(cardNo, true) % 10) + ""; 
        
        String checkDigit = digit.substring(digit.length() - 1);
        logger.debug("Card: " + cardNo
                + ", Sum: " + sum
                + ", Input:" + cardNo.substring(cardNo.length() - 1)
                + ", Calc:" + checkDigit);
        // Compare calculated digit with input card check digit.
        boolean digitCheck = cardNo.substring(cardNo.length() - 1).equals(checkDigit);
        return sumCheck && digitCheck;
    }
}
