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
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.solent.com504.oodd.bank.Card;
import org.solent.com504.oodd.cardchecker.CardChecker;
import org.solent.com504.oodd.cardchecker.CardValidationResult;
import org.solent.com504.oodd.cart.dao.impl.UserRepository;
import org.solent.com504.oodd.cart.model.dto.OrderItem;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import org.solent.com504.oodd.cart.model.service.ShoppingCart;
import org.solent.com504.oodd.cart.model.service.ShoppingService;
import org.solent.com504.oodd.cart.web.WebObjectFactory;
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
public class MVCController {

    final static Logger LOG = LogManager.getLogger(MVCController.class);

    // this could be done with an autowired bean
    //private ShoppingService shoppingService = WebObjectFactory.getShoppingService();
    @Autowired
    ShoppingService shoppingService;
    
    @Autowired
    UserRepository userRepo;

    // note that scope is session in configuration
    // so the shopping cart is unique for each web session
    @Autowired
    ShoppingCart shoppingCart;

    private User getSessionUser(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        LOG.debug("Got session user");
        if (sessionUser == null) {
            sessionUser = new User();
            sessionUser.setUsername("anonymous");
            sessionUser.setUserRole(UserRole.ANONYMOUS);
            session.setAttribute("sessionUser",sessionUser);
        }
        return sessionUser;
    }

    // this redirects calls to the root of our application to index.html

    /**
     * Root page
     * @param model mvc model
     * @return index.html redirect
     */
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        return "redirect:/index.html";
    }
    
    /**
     * View Cart Get method
     * @param action remove item from cart or just view
     * @param itemName name of the item to remove from cart
     * @param itemId itemId of the item to remove from cart
     * @param model mvc model
     * @param session web session
     * @return Cart page with shopping cart items
     */
    @RequestMapping(value = "/cart", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewCart(
            @RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemId", required = false) String itemId,
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        
        LOG.info("Get/Post Cart");
        
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "cart");

        String message = "";
        String errorMessage = "";
        
        if ("removeItemFromCart".equals(action)) {
            message = "removed " + itemName + " from cart";
            shoppingCart.removeItemFromCart(itemId);
        } 


        List<OrderItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();

        // populate model with values
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        LOG.warn(errorMessage);
        return "cart";
    }
    
    /**
     * Route for checkout page
     * @param model mvc model
     * @param session web session
     * @return Checkout page with all items and a form to insert card info
     */
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String viewCheckout(
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        LOG.info("Reached GET Checkout");
        
        List<User> purchaser = userRepo.findByUsername(sessionUser.getUsername());
        if(purchaser.size() > 0){
            User purchaseUser = purchaser.get(0);
            model.addAttribute("user", purchaseUser);    
        }
        
        // used to set tab selected
        model.addAttribute("selectedPage", "checkout");

        String message = "";
        String errorMessage = "";


        List<OrderItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();
        
        // populate model with values
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        LOG.warn(errorMessage);
        return "checkout";
    }
    
    /**
     * Checkout POST request
     * @param cardnumber card number from the page's form
     * @param cardname card name from the page's form
     * @param cardenddate card end date from the page's form
     * @param cardissuenumber card issue number from the page's form
     * @param cardcvv card cvv number from the page's form
     * @param model mvc model
     * @param session web session
     * @return the checkout page with a success or error message
     */
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String completeCheckout(
            @RequestParam(name = "cardnumber", required = false) String cardnumber,            
            @RequestParam(name = "cardname", required = false) String cardname,
            @RequestParam(name = "cardenddate", required = false) String cardenddate,
            @RequestParam(name = "cardissuenumber", required = false) String cardissuenumber,
            @RequestParam(name = "cardcvv", required = false) String cardcvv,
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        LOG.info("Checkout POST");
        
        // used to set tab selected
        model.addAttribute("selectedPage", "checkout");

        String message = "";
        String errorMessage = "";
        
        //Validate
        CardValidationResult result = CardChecker.checkValidity(cardnumber);
        LOG.info("Validating: " + cardnumber + " is valid: " + result.getIsValid() + " message: " + result.getMessage());

        
        
        if(!result.getIsValid()){
            errorMessage = result.getMessage();
            LOG.error("Checkout Failed: " + errorMessage);
        }
        else{
            
            //Pay & Create Order
            Card card = new Card();
            if(!card.setCVV(cardcvv)){
                errorMessage = "Invalid CVV";
            }
            if(!card.setCardnumber(cardnumber)){
                errorMessage = "Invalid Card Number";
            }
            if(!card.setEndDate(cardenddate)){
                errorMessage = "Invalid End Date - make sure the end date is later than the current date";
            }
            if(cardissuenumber != null && !card.setIssueNumber(cardissuenumber)){
                errorMessage = "Invalid Issue Number";
            }
            if(!card.setName(cardname)){
                errorMessage = "Invalid Card Name";
            }
            if(errorMessage.equals("")){
                
                //Check stock
                String stockMessage = shoppingService.checkStock(shoppingCart);
                if(stockMessage.equals("")){
                    try{
                        boolean purchased = shoppingService.purchaseItems(shoppingCart, sessionUser, card);
                        if(!purchased){
                            errorMessage = "Unable to purchase items. Please make sure you have entered your details correctly and that you have enough money in your account";
                            LOG.error("Checkout Failed: " + errorMessage);
                        }
                        else{
                            message = "Successfully purchased items";
                        }
                    }
                    catch(Exception ex){
                        LOG.debug(ex);
                        errorMessage = "Unable to connect to the bank API. Please get your admin to check the URL, username, password and shopkeeper card are all correct";
                    }

                }
                else{
                    errorMessage = stockMessage;
                }
            }
            else{
                LOG.error("Checkout Failed: " + errorMessage);
            }
        }
        
        List<OrderItem> shoppingCartItems = shoppingCart.getShoppingCartItems();
        Double shoppingcartTotal = shoppingCart.getTotal();
        

        // populate model with values
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        LOG.warn(errorMessage);
        return "checkout";
    }

    /**
     * Home page route which shows all catalog items
     * @param action can be add/remove item to cart or search
     * @param itemName name of item to add
     * @param itemId id of item to remove from cart
     * @param searchQuery query to search through all the catalog items 
     * @param category the category of items to look in
     * @param model mvc model
     * @param session web session
     * @return home page with catalog items
     */
    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewHome(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemId", required = false) String itemId,            
            @RequestParam(name = "searchQuery", required = false) String searchQuery,            
            @RequestParam(name = "category", required = false) String category,


            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);
        LOG.info("Home - Get/Post - ", action);
        
        // used to set tab selected
        model.addAttribute("selectedPage", "home");

        String message = "";
        String errorMessage = "";

        // note that the shopping cart is is stored in the sessionUser's session
        // so there is one cart per sessionUser
//        ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("shoppingCart");
//        if (shoppingCart == null) synchronized (this) {
//            if (shoppingCart == null) {
//                shoppingCart = WebObjectFactory.getNewShoppingCart();
//                session.setAttribute("shoppingCart", shoppingCart);
//            }
//        }
        if (action == null) {
            // do nothing but show page
        } else if ("addItemToCart".equals(action)) {
            ShoppingItem shoppingItem = shoppingService.getNewItemByName(itemName);
            OrderItem item = shoppingCart.getItemFromCart(shoppingItem);
            if (shoppingItem == null) {
                message = "cannot add unknown " + itemName + " to cart";
            } 
            else if(item != null && shoppingItem.getQuantity() <= item.getQuantity()){
                message = "cannot add item " + itemName + "as you've got the max quantity in your cart";
            }
            else {
                message = "adding " + itemName + " to cart price= " + shoppingItem.getPrice();
                shoppingCart.addItemToCart(shoppingItem);
            }
        } else if ("search".equals(action) && searchQuery != null){
            message = "searched for: " +searchQuery; 
        }
        else {
            message = "unknown action=" + action;
        }
        
        
        List<ShoppingItem> availableItems = null;
        if (category == null && "search".equals(action) && searchQuery != null){
            availableItems = shoppingService.searchAvailableItems(searchQuery);  
        }
        else if(category != null){
            availableItems = shoppingService.getAvailableByCategory(category);
        }
        else{
            availableItems = shoppingService.getAvailableItems();
        }

        
        List<String> categories = shoppingService.getAvailableCategories();
        List<OrderItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();

        // populate model with values
        model.addAttribute("availableItems", availableItems);        
        model.addAttribute("categories", categories);
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        
        LOG.warn(errorMessage);
        return "home";
    }

    /*
     * Default exception handler, catches all exceptions, redirects to friendly
     * error page. Does not catch request mapping errors
     */

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
