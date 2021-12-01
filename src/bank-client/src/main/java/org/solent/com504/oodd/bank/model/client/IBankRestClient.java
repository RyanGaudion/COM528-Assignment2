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
package org.solent.com504.oodd.bank.model.client;

import org.solent.com504.oodd.bank.TransactionRequest;
import org.solent.com504.oodd.bank.TransactionResponse;

/**
 * Interface for the Bank API Client
 *
 * @author rgaud
 */
public interface IBankRestClient {

    /**
     * Transfer Money with no authentication
     *
     * @param request the transaction request to send to the API
     * @return the Transaction response from the api
     */
    public TransactionResponse transferMoney(TransactionRequest request);

    /**
     * Transfer money with http authentication
     *
     * @param request the transaction request to send to the API
     * @param userName the username to authenticate with
     * @param password the password to authenticate with
     * @return the Transaction response from the api
     */
    public TransactionResponse transferMoney(TransactionRequest request, String userName, String password);
}
