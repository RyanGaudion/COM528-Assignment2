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
package org.solent.com504.oodd.cart.dao.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.dao.impl.ShoppingItemCatalogRepository;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.dao.impl.UserRepository;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.solent.com504.oodd.cart.model.dto.OrderItem;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 *
 * @author cgallen
 */
@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from the OrderServiceConfig class
@ContextConfiguration(classes = DAOTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
@Transactional
public class InvoiceRepositoryTest {

    private static final Logger LOG = LogManager.getLogger(InvoiceRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingItemCatalogRepository shoppingItemCatalogRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    /**
     * Tests adding of invoices to the DB and the retrieval too
     */
    @Test
    public void testInvoice() {
        LOG.debug("****************** starting test");

        invoiceRepository.deleteAll();

        User user1 = new User();
        user1.setFirstName("craig");
        user1.setSecondName("gallen");
        user1.setUsername("craigUser1");
        user1 = userRepository.save(user1);
        assertEquals(1, userRepository.count());

        Invoice invoice1 = new Invoice();
        invoice1.setAmountDue(100.0);
        invoice1.setDateOfPurchase(new Date());
        invoice1.setPurchaser(user1);

        invoice1 = invoiceRepository.save(invoice1);
        assertEquals(1, invoiceRepository.count());

        // do catalog item
        ShoppingItem shoppingItem1 = new ShoppingItem();
        shoppingItem1.setName("item 1");
        shoppingItem1.setPrice(100.1);
        shoppingItem1.setQuantity(1);
        //shoppingItem1(UUID.randomUUID().toString());
        shoppingItem1 = shoppingItemCatalogRepository.save(shoppingItem1);

        List<OrderItem> purchasedItems = new ArrayList<OrderItem>();

        invoice1.setPurchasedItems(purchasedItems);
        invoice1 = invoiceRepository.save(invoice1);
        
        assertEquals(1, invoiceRepository.findByPurchaser_Id(user1.getId()).size());        
        assertEquals(0, invoiceRepository.findByPurchaser_Id(14L).size());
        assertEquals(1, invoiceRepository.findByPurchaser_UsernameContainingIgnoreCase("user1").size());        
        assertEquals(0, invoiceRepository.findByPurchaser_UsernameContainingIgnoreCase("user2").size());



        Optional<Invoice> optional = invoiceRepository.findById(invoice1.getId());
        Invoice foundInvoice = optional.get();

        LOG.debug("found Invoice : " + foundInvoice);

        LOG.debug("****************** test complete");
    }

}
