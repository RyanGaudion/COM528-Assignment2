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
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repo for accessing Shopping Items in DB
 * @author rgaud
 */
@Repository
public interface ShoppingItemCatalogRepository  extends JpaRepository<ShoppingItem,Long>{

    /**
     * Find shopping items by name
     * @param name name of the item to find
     * @return items that match the search query
     */
    @Query("select i from ShoppingItem i where lower(i.name) like lower(concat('%', :name,'%'))")
    public List<ShoppingItem> findByName(@Param("name")String name);
}
