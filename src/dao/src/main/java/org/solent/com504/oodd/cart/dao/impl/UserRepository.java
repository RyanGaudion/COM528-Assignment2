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
import org.solent.com504.oodd.cart.model.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * User repo for accessing user objects from DB
 * @author cgallen
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by username
     * @param username username string to search by
     * @return matching user
     */
    @Query("select u from User u where u.username = :username")
    public List<User> findByUsername(@Param("username")String username);

    /** 
     * Find a user by names (First & last)
     * @param firstName first name of the user 
     * @param secondName last name of the user
     * @return list of matching users
     */
    @Query("select u from User u where u.firstName = :firstName and u.secondName = :secondName")
    public List<User> findByNames(@Param("firstName") String firstName, @Param("secondName") String secondName);

}
