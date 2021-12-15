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
package org.solent.com504.oodd.cart.spring.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.bank.Card;
import org.solent.com504.oodd.bank.Transaction;
import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.solent.com504.oodd.cart.model.dto.InvoiceStatus;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.cart.model.service.IBankingService;
import static org.solent.com504.oodd.cart.spring.web.CatalogController.LOG;
import static org.solent.com504.oodd.cart.spring.web.MVCController.LOG;
import static org.solent.com504.oodd.cart.spring.web.UserAndLoginController.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author rgaud
 */
@Controller
@RequestMapping("/")
public class InvoiceController {
    
    @Autowired
    InvoiceRepository invoiceRepo;
    
    @Autowired
    IBankingService bankingService;
    
    final static Logger LOG = LogManager.getLogger(InvoiceController.class);

    
    private User getSessionUser(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        LOG.info("Got session user");
        if (sessionUser == null) {
            sessionUser = new User();
            sessionUser.setUsername("anonymous");
            sessionUser.setUserRole(UserRole.ANONYMOUS);
            session.setAttribute("sessionUser", sessionUser);
        }
        return sessionUser;
    }
    
    /**
     * Gets all orders for the orders page
     * @param model mvc model
     * @param session web session
     * @param action either no action or search
     * @param searchQuery query to search/filter the orders by
     * @return the order page with either all or searched orders
     */
    @RequestMapping(value = {"/orders"}, method = RequestMethod.GET)
    @Transactional
    public String orders(Model model,
            HttpSession session,
            @RequestParam(name = "action", required = false) String action,         
            @RequestParam(name = "searchQuery", required = false) String searchQuery) {
        
        String message = "";
        String errorMessage = "";

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        
        LOG.debug("Fetch all orders");
        
        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            errorMessage = "you must be an administrator to access users information";
            return "home";
        }
        
        List<Invoice> invoiceList;
        if(action != null && action.equals("search")){
            invoiceList = invoiceRepo.findByPurchaser_UsernameContainingIgnoreCase(searchQuery);
        }
        else{
            invoiceList = invoiceRepo.findAll();
        }
        

        model.addAttribute("ordersListSize", invoiceList.size());
        model.addAttribute("ordersList", invoiceList);
        model.addAttribute("selectedPage", "adminOrders");
        LOG.warn(errorMessage);
        return "orders";
    }
    
    /**
     * Gets the orders page of only the user's orders
     * @param model mvc model
     * @param session web session
     * @return orders page
     */
    @RequestMapping(value = {"/myOrders"}, method = RequestMethod.GET)
    @Transactional
    public String myOrders(Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        LOG.info("Getting my orders");
        
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        List<Invoice> invoiceList = invoiceRepo.findByPurchaser_Id(sessionUser.getId());

        model.addAttribute("ordersListSize", invoiceList.size());
        model.addAttribute("ordersList", invoiceList);
        model.addAttribute("selectedPage", "myOrders");
        LOG.warn(errorMessage);
        return "orders";
    }
    
    /**
     * Gets the viewmodifyorder page
     * @param orderid the id of the order to edit
     * @param model mvc model
     * @param session web session
     * @return the viewmodifyorder page
     */
    @RequestMapping(value = {"/viewModifyOrder"}, method = RequestMethod.GET)
    public String viewOrder(
            @RequestParam(value = "orderid", required = true) Long orderid,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        model.addAttribute("selectedPage", "viewModifyOrder");

        LOG.debug("get viewModifyOrder called for orderId=" + orderid);

        // check secure access to modifyUser profile
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        Optional<Invoice> orderList = invoiceRepo.findById(orderid);
        if (orderList.isEmpty()) {
            LOG.error("viewModifyOrder called for unknown order=" + orderid);
            return ("redirect:/home");
        }

        Invoice modifyOrder = orderList.get();
        LOG.info("Item purchaser: " + modifyOrder.getPurchaser().getUsername());
        LOG.info("Username: " + sessionUser.getUsername());
        if(sessionUser == null || (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole()) && !modifyOrder.getPurchaser().getUsername().equals(sessionUser.getUsername()))){
            LOG.error("viewModifyOrder called for wrong user: " + orderid);
            return ("redirect:/home");
        }
        
        
        model.addAttribute("modifyOrder", modifyOrder);

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        LOG.warn(errorMessage);
        return "viewModifyOrder";
    }
    
    /**
     * POST request for modifying an order
     * @param id ID of the order to modify
     * @param action either refund or update
     * @param orderStatus new order status for the order
     * @param model mvc model
     * @param session web session
     * @return the viewmodifyorder page
     */
    @RequestMapping(value = {"/viewModifyOrder"}, method = RequestMethod.POST)
    public String modifyOrder(
            @RequestParam(value = "id", required = true) Long id,              
            @RequestParam(value = "action", required = false) String action,            
            @RequestParam(value = "orderStatus", required = false) String orderStatus,

            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";
        
        LOG.debug("View modify order: " + action);
        
        // security check if party is allowed to access or modify this invoice
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            errorMessage = "you must be admin to edit order information";
            model.addAttribute("errorMessage", errorMessage);
            return "redirect:/home";
        }

        Optional<Invoice> orderList = invoiceRepo.findById(id);
        if (orderList.isEmpty()) {
            errorMessage = "update order called for unknown id:" + id;
            LOG.warn(errorMessage);
            model.addAttribute("errorMessage", errorMessage);
            return ("redirect:/home");
        }

        Invoice modifyOrder = orderList.get();

        if(action != null && action.equals("refund")){
            try{
                Card refundTo = new Card();
                refundTo.setCardnumber(modifyOrder.getPurchaserCard());
                Transaction transaction = bankingService.refundSimpleTransaction(refundTo, modifyOrder.getAmountDue());
                if(transaction.getTransactionResponse().getStatus().equals("SUCCESS")){
                    modifyOrder.setRefunded(true);
                    modifyOrder = invoiceRepo.save(modifyOrder);
                    message = "Successfully refunded order";
                }
                else{
                    errorMessage = "Unable to refund order: " + transaction.getTransactionResponse().getMessage();
                }
                
            }
            catch(Exception ex){
                errorMessage = "Unable to refund item - please try again later";
            }
        }
        else{
            // update status
            try {
                InvoiceStatus status = InvoiceStatus.valueOf(orderStatus);
                modifyOrder.setInvoiceStatus(status);
                modifyOrder = invoiceRepo.save(modifyOrder);
                message = "Order " + modifyOrder.getId()+ " updated successfully";
            } catch (Exception ex) {
                LOG.warn("cannot parse order status" + orderStatus);
                model.addAttribute("errorMessage", errorMessage);
                return ("viewModifyOrder");
            }
        }


        

        model.addAttribute("modifyOrder", modifyOrder);

        // add message if there are any 
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("message", message);

        model.addAttribute("selectedPage", "viewModifyOrder");
        LOG.warn(errorMessage);
        return "viewModifyOrder";
    }
    
    /**
     * Exception handler page 
     * @param e exception to show
     * @param model mvc model
     * @param request web request
     * @return error page
     */

    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model, HttpServletRequest request) {
        LOG.error(e);
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        final String strStackTrace = sw.toString(); // stack trace as a string
        String urlStr = "not defined";
        if (request != null) {
            StringBuffer url = request.getRequestURL();
            urlStr = url.toString();
        }
        model.addAttribute("requestUrl", urlStr);
        model.addAttribute("strStackTrace", strStackTrace);
        model.addAttribute("exception", e);
        //logger.error(strStackTrace); // send to logger first
        return "error"; // default friendly exception message for sessionUser
    }
}
