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
package org.solent.com504.oodd.cart.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.bank.Card;
import org.solent.com504.oodd.bank.Transaction;
import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.dao.impl.ShoppingItemCatalogRepository;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.solent.com504.oodd.cart.model.dto.InvoiceStatus;
import org.solent.com504.oodd.cart.model.dto.OrderItem;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.service.IBankingService;
import org.solent.com504.oodd.cart.model.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

/**
 *
 * @author cgallen
 */
@Service
@DependsOn({"populate-database"})
public class ShoppingServiceImpl implements ShoppingService {

    private static final Logger LOG = LogManager.getLogger(ShoppingServiceImpl.class);    
    @Autowired
    ShoppingItemCatalogRepository shoppingItemRepo;
    
    @Autowired
    InvoiceRepository invoiceRepo;

    @Autowired
    IBankingService bankingService;

    public ShoppingServiceImpl() {

    }

    
    @Override
    public List<ShoppingItem> getAvailableItems() {
        return shoppingItemRepo.findAll();
    }
    
    @Override 
    public String checkStock(ShoppingCart cart){
        for (OrderItem orderItem : cart.getShoppingCartItems()) {
                //Fetch Item from DB first
                ShoppingItem shoppingItem = shoppingItemRepo.findByName(orderItem.getItem().getName()).get(0);
                if(shoppingItem != null){
                    //Check Quantity
                    if((shoppingItem.getQuantity() - orderItem.getQuantity()) < 0){
                        return "Error for Item: " + shoppingItem.getName() + "   -  We only have " + shoppingItem.getQuantity() + " in stock and you're trying to order " + orderItem.getQuantity();
                    }
                }
                LOG.info(shoppingItem);
        }
        return "";
    }

    @Override
    public boolean purchaseItems(ShoppingCart shoppingCart, User purchaser, Card purchaserCard) {
        LOG.info("purchased items:");
        
        for (OrderItem shoppingItem : shoppingCart.getShoppingCartItems()) {
            //Fetch Item from DB first
            shoppingItem.setItem(shoppingItemRepo.findByName(shoppingItem.getItem().getName()).get(0));
            LOG.info(shoppingItem);
        }
                
        //Create Invoice in DB
        Invoice newInvoice = new Invoice();
        newInvoice.setAmountDue(shoppingCart.getTotal());
        newInvoice.setPurchasedItems(shoppingCart.getShoppingCartItems());
        newInvoice.setPurchaser(purchaser);
        newInvoice.setDateOfPurchase(new Date());
        newInvoice.setInvoiceStatus(InvoiceStatus.BACKLOG);

        
        //Send money with api
        Transaction result = bankingService.sendTransaction(purchaserCard, newInvoice.getAmountDue());
        if(result.getTransactionResponse().getStatus().toLowerCase().equals("success")){
            //If  success - save invoice
            invoiceRepo.save(newInvoice);
            
            
            //Reduce Stock amount
            for (OrderItem orderItem : newInvoice.getPurchasedItems()) {
                //Fetch Item from DB first
                ShoppingItem shoppingItem = shoppingItemRepo.findByName(orderItem.getItem().getName()).get(0);
                if(shoppingItem != null){
                    //Reduce Quantity
                    shoppingItem.setQuantity(shoppingItem.getQuantity() - orderItem.getQuantity());
                    shoppingItemRepo.save(shoppingItem);
                }
                LOG.info(shoppingItem);
            } 
            
            //Empty Cart
            shoppingCart.clearCart();
            return true;
        }
        
        //If error return false
        return false;
    }

    @Override
    public ShoppingItem getNewItemByName(String name) {
        ShoppingItem templateItem = shoppingItemRepo.findByName(name).get(0);
        
        if(templateItem==null) return null;
        
        ShoppingItem item = new ShoppingItem();
        item.setName(name);
        item.setPrice(templateItem.getPrice());
        item.setQuantity(0);
        item.setUuid(UUID.randomUUID().toString());
        return item;
    }

    @Override
    public List<ShoppingItem> searchAvailableItems(String searchQuery) {
        if(searchQuery == null || searchQuery.length() == 0){
            return getAvailableItems();
        }
        else{
            return shoppingItemRepo.findByName(searchQuery);
        }
    }

}
