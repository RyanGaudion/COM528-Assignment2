/*
 * Copyright 2021 rgaud & Steven Hawkins.
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

import java.util.AbstractMap;
import java.util.Map;

/**
 * Class containing test data for the cardChecker project. 
 * @author rgaudion
 */
public class TestResources {

    /**
     * Get a List of pairs representing valid Credit card numbers and their
     * associated card company.
     * @return java.util.List<java.util.Map.Entry<CardCompany, String>>
     */
    public static java.util.List<java.util.Map.Entry<CardCompany, String>> getCardPairs() {
        java.util.List<java.util.Map.Entry<CardCompany, String>> cards = new java.util.ArrayList<>();
        Map.Entry<CardCompany, String> pair1 = new AbstractMap.SimpleEntry<>(CardCompany.AMERICAN_EXPRESS, "3782 8224 6310 005");
        cards.add(pair1);
        Map.Entry<CardCompany, String> pair2 = new AbstractMap.SimpleEntry<>(CardCompany.AMERICAN_EXPRESS, "371449635398431");
        cards.add(pair2);
        Map.Entry<CardCompany, String> pair3 = new AbstractMap.SimpleEntry<>(CardCompany.AMERICAN_EXPRESS, "378734493671000");
        cards.add(pair3);
//         Entry<CardCompany, String> pair4 = new SimpleEntry<>(CardCompany.DINERS_CLUB, "30569309025904");
//         cards.add(pair4); UNKNOWN
//        Entry<CardCompany, String> pair5 = new SimpleEntry<>(CardCompany.DINERS_CLUB, "38520000023237");
//        cards.add(pair5);
        Map.Entry<CardCompany, String> pair6 = new AbstractMap.SimpleEntry<>(CardCompany.DISCOVER, "6011111111111117");
        cards.add(pair6);
        Map.Entry<CardCompany, String> pair7 = new AbstractMap.SimpleEntry<>(CardCompany.DISCOVER, "6011000990139424");
        cards.add(pair7);
        Map.Entry<CardCompany, String> pair8 = new AbstractMap.SimpleEntry<>(CardCompany.JCB, "3530111333300000");
        cards.add(pair8);
        pair8 = new AbstractMap.SimpleEntry<>(CardCompany.JCB, "3566002020360505");
        cards.add(pair8);
        Map.Entry<CardCompany, String> pair9 = new AbstractMap.SimpleEntry<>(CardCompany.MASTERCARD, "5555555555554444");
        cards.add(pair9);
        Map.Entry<CardCompany, String> pair10 = new AbstractMap.SimpleEntry<>(CardCompany.MASTERCARD, "5105105105105100");
        cards.add(pair10);
        Map.Entry<CardCompany, String> pair11 = new AbstractMap.SimpleEntry<>(CardCompany.VISA, "4111111111111111");
        cards.add(pair11);
        Map.Entry<CardCompany, String> pair12 = new AbstractMap.SimpleEntry<>(CardCompany.VISA, "4012888888881881");
        cards.add(pair12);
        Map.Entry<CardCompany, String> pair13 = new AbstractMap.SimpleEntry<>(CardCompany.VISA, "4222222222222");
        cards.add(pair13);
        return cards;
    }
    
    /**
     * Get a string array of invalid card numbers.
     * @return String[]
     */
    public static String[] getInvalidCards() {
        return new String[]{"", "abcdefghijklmno", "11111111111111", "0", "55555555abc554444", "123456 abc 124"};
    }

}
