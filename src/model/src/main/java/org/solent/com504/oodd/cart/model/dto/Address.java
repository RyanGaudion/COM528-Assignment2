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

import javax.persistence.Embeddable;

/**
 *
 * @author rgaud
 */
@Embeddable
public class Address {

    private String houseNumber;

    private String addressLine1;

    private String addressLine2;
    
    private String county;
    
    private String city;

    private String postcode;

    private String mobile;

    private String telephone;

    private String country;

    /**
     * Gets house number
     * @return String
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Set house number
     * @param houseNumber
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Get address line 1
     * @return
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * set address line 1
     * @param addressLine1
     */
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    /**
     * get address line 2
     * @return
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * set address line 2 
     * @param addressLine2
     */
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    /**
     * get postcode
     * @return
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * set postcode
     * @param postcode
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * get mobile of address
     * @return
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * set mobile of address
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * get telephone of address
     * @return
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * set telephone of address
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * get country of address
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * set country of address
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * get county of address
     * @return
     */
    public String getCounty() {
        return county;
    }

    /**
     * set county of address
     * @param county
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * get city of the address
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * set city of the address
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" + "houseNumber=" + houseNumber + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", county=" + county + ", city=" + city + ", postcode=" + postcode + ", mobile=" + mobile + ", telephone=" + telephone + ", country=" + country + '}';
    }
    
    


    
    
}
