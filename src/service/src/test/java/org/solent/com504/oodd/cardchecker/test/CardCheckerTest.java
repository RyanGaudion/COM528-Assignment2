/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cardchecker.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.solent.com504.oodd.cardchecker.CardChecker;
import org.solent.com504.oodd.cardchecker.CardCompany;
import org.solent.com504.oodd.cardchecker.CardValidationResult;
/**
 *
 * @author rgaudion
 */
public class CardCheckerTest {

    public static Logger logger = LogManager.getLogger(CardCheckerTest.class);
    static List<Entry<CardCompany, String>> cards = new ArrayList<>();
    static String[] invalidCards;

    public CardCheckerTest() {
    }

    @Before
    public void setUpClass() {
        logger.debug(CardCheckerTest.class + " Setup the test");
        cards = TestResources.getCardPairs();
        invalidCards = TestResources.getInvalidCards();
    }

    /**
     * Test of isValid method, of class CardChecker.
     */
    @Test
    public void testCheckValidityBadInputs() {
        logger.debug("testCheckValidityBadInputs");
        for (String cardIn : invalidCards) {
            boolean expResult = false;
            CardValidationResult result = CardChecker.checkValidity(cardIn);
            assertEquals(expResult, result.getIsValid());
        }
    }

    /**
     * Test of isValid method, of class CardChecker.
     */
    @Test
    public void testCheckValidity() {
        logger.debug("testCheckValidity");
        for (Entry<CardCompany, String> pair : cards) {
            boolean expResult = true;
            CardValidationResult result = CardChecker.checkValidity(pair.getValue());
            assertEquals(expResult, result.getIsValid());
            assertEquals(pair.getKey(), result.getCardCompany());
        }
    }
}

