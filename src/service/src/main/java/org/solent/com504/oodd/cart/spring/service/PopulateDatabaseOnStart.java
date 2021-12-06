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
package org.solent.com504.oodd.cart.spring.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.dao.impl.ShoppingItemCatalogRepository;
import org.solent.com504.oodd.cart.dao.impl.UserRepository;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.solent.com504.oodd.cart.model.dto.InvoiceStatus;
import org.solent.com504.oodd.cart.model.dto.OrderItem;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author cgallen
 */
@Component("populate-database")
public class PopulateDatabaseOnStart {

    private static final Logger LOG = LogManager.getLogger(PopulateDatabaseOnStart.class);

    private static final String DEFAULT_ADMIN_USERNAME = "globaladmin";
    private static final String DEFAULT_ADMIN_PASSWORD = "globaladmin";

    private static final String DEFAULT_USER_PASSWORD = "user1234";
    private static final String DEFAULT_USER_USERNAME = "user1234";

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private InvoiceRepository invoiceRepo;

    
    @Autowired
    private ShoppingItemCatalogRepository catalogRepo;
    
    /**
     * Method that adds initial users, catalog items & orders to the system
     */
    @PostConstruct
    public void initDatabase() {
        LOG.debug("initialising database with startup values");

        // initialising admin and normal user if dont exist
        User adminUser = new User();
        adminUser.setUsername(DEFAULT_ADMIN_USERNAME);
        adminUser.setFirstName("default administrator");
        adminUser.setPassword(DEFAULT_ADMIN_PASSWORD);
        adminUser.setUserRole(UserRole.ADMINISTRATOR);

        List<User> users = userRepository.findByUsername(DEFAULT_ADMIN_USERNAME);
        if (users.isEmpty()) {
            userRepository.save(adminUser);
            LOG.info("creating new default admin user:" + adminUser);
        } else {
            LOG.info("default admin user already exists. Not creating new :" + adminUser);
        }

        User defaultUser = new User();
        defaultUser.setUsername(DEFAULT_USER_USERNAME);
        defaultUser.setFirstName("default user");
        defaultUser.setPassword(DEFAULT_USER_PASSWORD);
        defaultUser.setUserRole(UserRole.CUSTOMER);

        users = userRepository.findByUsername(DEFAULT_USER_USERNAME);
        if (users.isEmpty()) {
            userRepository.save(defaultUser);
            LOG.info("creating new default user:" + defaultUser);
        } else {
            LOG.info("defaultuser already exists. Not creating new :" + defaultUser);
        }

        addShoppingItem("house", 20000.00, 3);        
        addShoppingItem("hen", 5.00, 1);        
        addShoppingItem("car", 5000.00, 0);        
        addShoppingItem("pet alligator", 65.00, 3);        
        addShoppingItem("elephant", 100.00, 2);        
        addShoppingItem("dog", 65.00, 2); 
        addShoppingItem("cat", 10.50, 2); 
        addShoppingItem("pen", 2.50, 0); 
 
        addOrder("cat", defaultUser);        
        addOrder("dog", defaultUser);
        addOrder("car", adminUser);

        
        LOG.debug("database initialised");
    }
    
        private void addShoppingItem(String name, Double price, Integer quantity){
            ShoppingItem item = new ShoppingItem();
            item.setName(name);
            item.setPrice(price);
            item.setQuantity(quantity);
            List<ShoppingItem> itemsFound = catalogRepo.findByName(name);
            if(itemsFound.size() < 1){
                catalogRepo.save(item);
                LOG.info("Added Item :" + name);
            }
        }
        private void addOrder(String itemName, User user){
            List<ShoppingItem> item = catalogRepo.findByName(itemName);
            if(item.size() > 0){
                OrderItem orderedItem = new OrderItem(item.get(0), 1);
                List<OrderItem> items = new ArrayList<>();
                items.add(orderedItem);

                Invoice invoice = new Invoice();
                invoice.setPurchasedItems(items);
                invoice.setPurchaser(user);
                invoice.setDateOfPurchase(new Date());
                invoice.setInvoiceStatus(InvoiceStatus.BACKLOG);
                invoice.setAmountDue(item.get(0).getPrice());
                invoiceRepo.save(invoice);
            }
        }
}
