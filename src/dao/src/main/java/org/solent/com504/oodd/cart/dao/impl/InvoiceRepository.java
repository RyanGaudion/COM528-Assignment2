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
package org.solent.com504.oodd.cart.dao.impl;


import java.util.List;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Invoice/Orders repo for DB
 * @author rgaud
 */
@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice,Long>{

    /**
     * Finds all invoice by a specific User's ID
     * @param id ID of the user to search by
     * @return a list of invoices
     */
    public List<Invoice> findByPurchaser_Id(Long id);
    
    /**
     * Finds all invoices by a search string that searches against username
     * @param username username search string
     * @return all invoice that user's username matches the search string
     */
    public List<Invoice> findByPurchaser_UsernameContainingIgnoreCase(String username);

}