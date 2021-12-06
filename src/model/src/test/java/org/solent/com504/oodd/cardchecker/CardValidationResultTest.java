/*
 * Copyright 2021 Steven Hawkins.
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

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Steven Hawkins 5hawks48@solent.ac.uk
 */
public class CardValidationResultTest {
    public static Logger logger = LogManager.getLogger(CardValidationResult.class);
    
    public CardValidationResultTest() {
    }

    /**
     * Test of getMessage method, of class CardValidationResult.
     */
    @Test
    public void testGetMessage() {
        logger.debug("getMessage test");
        CardValidationResult instance = new CardValidationResult(false, "error msg");
        String expResult = "error msg";
        String result = instance.getMessage();
        assertEquals(expResult, result);
    }

    /**
     * Test of getIsValid method, of class CardValidationResult.
     */
    @Test
    public void testGetIsValid() {
        logger.debug("getIsValid test");
        CardValidationResult instance = new CardValidationResult(false, "error msg");;
        Boolean expResult = false;
        Boolean result = instance.getIsValid();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCardCompany method, of class CardValidationResult.
     */
    @Test
    public void testGetCardCompany() {
        logger.debug("getCardCompany test");
        CardValidationResult instance = new CardValidationResult("msg", CardCompany.JCB);;
        CardCompany expResult = CardCompany.JCB;
        CardCompany result = instance.getCardCompany();
        assertEquals(expResult, result);
    }
    
}
