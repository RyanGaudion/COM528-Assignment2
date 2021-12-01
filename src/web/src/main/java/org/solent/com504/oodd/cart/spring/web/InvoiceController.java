/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.spring.web;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.solent.com504.oodd.cart.dao.impl.InvoiceRepository;
import org.solent.com504.oodd.cart.model.dto.Invoice;
import org.solent.com504.oodd.cart.model.dto.InvoiceStatus;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import static org.solent.com504.oodd.cart.spring.web.UserAndLoginController.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    
    private User getSessionUser(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            sessionUser = new User();
            sessionUser.setUsername("anonymous");
            sessionUser.setUserRole(UserRole.ANONYMOUS);
            session.setAttribute("sessionUser", sessionUser);
        }
        return sessionUser;
    }
    
    @RequestMapping(value = {"/orders"}, method = RequestMethod.GET)
    @Transactional
    public String orders(Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            errorMessage = "you must be an administrator to access users information";
            return "home";
        }

        List<Invoice> invoiceList = invoiceRepo.findAll();

        model.addAttribute("ordersListSize", invoiceList.size());
        model.addAttribute("ordersList", invoiceList);
        model.addAttribute("selectedPage", "orders");
        return "orders";
    }
    
    @RequestMapping(value = {"/myOrders"}, method = RequestMethod.GET)
    @Transactional
    public String myOrders(Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        List<Invoice> invoiceList = invoiceRepo.findByPurchaser_Id(sessionUser.getId());

        model.addAttribute("ordersListSize", invoiceList.size());
        model.addAttribute("ordersList", invoiceList);
        model.addAttribute("selectedPage", "myOrders");
        return "orders";
    }
    
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
        return "viewModifyOrder";
    }
    
    
    @RequestMapping(value = {"/viewModifyOrder"}, method = RequestMethod.POST)
    public String modifyOrder(
            @RequestParam(value = "id", required = true) Long id,            
            @RequestParam(value = "orderStatus", required = true) String orderStatus,

            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";
        
        // security check if party is allowed to access or modify this party
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


        // update status
        try {
            InvoiceStatus status = InvoiceStatus.valueOf(orderStatus);
            modifyOrder.setInvoiceStatus(status);
        } catch (Exception ex) {
            LOG.warn("cannot parse order status" + orderStatus);
            model.addAttribute("errorMessage", errorMessage);
            return ("viewModifyOrder");
        }

        modifyOrder = invoiceRepo.save(modifyOrder);

        model.addAttribute("modifyOrder", modifyOrder);

        // add message if there are any 
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("message", "Order " + modifyOrder.getId()+ " updated successfully");

        model.addAttribute("selectedPage", "viewModifyUser");

        return "viewModifyOrder";
    }
}
