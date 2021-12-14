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
package org.solent.com504.oodd.cart.model.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Embedded;
import org.solent.com504.oodd.bank.Card;
import org.solent.com504.oodd.password.PasswordUtils;

/**
 *
 * @author rgaud
 */
@Entity
public class User {

    private Long id;

    private String firstName = "";

    private String secondName = "";

    private String username = "";

    private String password = "";

    private String hashedPassword = "";

    private Address address;
    
    private Card savedCard;

    private UserRole userRole;

    private Boolean enabled = true;

    /**
     * Gets the user's unique ID
     * @return
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Sets the user's ID
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the role of the user 
     * @return
     */
    public UserRole getUserRole() {
        return userRole;
    }

    /**
     * Sets the role of the user
     * @param userRole
     */
    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    /**
     * Gets the username of the user
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the user's address
     * @return
     */
    @Embedded
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the user's address
     * @param address
     */
    public void setAddress(Address address) {
        this.address = address;
    }
    
    /**
     * Get the user's saved card
     * @return
     */
    @Embedded
    public Card getSavedCard() {
        return savedCard;
    }

    /**
     * Sets the user's saved card
     * @param card
     */
    public void setSavedCard(Card card) {
        card.clearCVV();
        this.savedCard = card;
    }

    /**
     * Get the user's firstname 
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the user's firstname
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the user's second name 
     * @return
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * Set the user' second name
     * @param secondName
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * Get whether the user is enabled
     * @return
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Set whether the user is enabled
     * @param enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    // passwords not saved in database only passwordhash is saved

    /**
     * Get the password of the user (not stored in DB)
     * @return
     */
    @Transient
    public String getPassword() {
        return password;
    }

    // generate hashed password to save in database

    /**
     * Set the user's password (also sets the hashed password)
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
        setHashedPassword(PasswordUtils.hashPassword(password));
    }

    /**
     * Checks if password is valid by comparing hashed password with checkPassword
     * @param checkPassword
     * @return
     */
    public boolean isValidPassword(String checkPassword) {
        if (checkPassword == null || getHashedPassword() == null) {
            return false;
        }
        return PasswordUtils.checkPassword(checkPassword, getHashedPassword());
    }

    /**
     * Gets the hashed password
     * @return
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Sets the hashed password
     * @param hashedPassword
     */
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    // no password or hashed password
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", firstName=" + firstName + ", secondName=" + secondName + ", username=" + username + ", password=NOT SHOWN, address=" + address + ", userRole=" + userRole + '}';
    }

}
