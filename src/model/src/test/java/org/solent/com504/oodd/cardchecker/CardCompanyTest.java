/*
 * Copyright 2021 rgaud & Steven Hawkins .
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

import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * @author rgaudion
 */
public class CardCompanyTest {
    public static Logger logger = LogManager.getLogger(CardCompanyTest.class);
    
    public CardCompanyTest() {
    }
    
    /**
     * Test of detect method, of class CardCompany with valid inputs..
     */
    @Test
    public void testDetect() {
        logger.debug(CardCompanyTest.class + ", CardCompany Detect test.");
        for (Map.Entry<CardCompany, String> pair : TestResources.getCardPairs()) {
            assertEquals(pair.getKey(), CardCompany.detect(pair.getValue()));
        }      
    }
    
    /**
     * Test of detect method, of class CardCompany with invalid inputs.
     */
    @Test
    public void testDetectInvalidCards() {
        logger.debug(CardCompanyTest.class + ", CardCompany Detect invalid cards test.");
        for (String card : TestResources.getInvalidCards()) {
            assertEquals(CardCompany.UNKNOWN, CardCompany.detect(card));
        }      
    }
    
    
    
}
