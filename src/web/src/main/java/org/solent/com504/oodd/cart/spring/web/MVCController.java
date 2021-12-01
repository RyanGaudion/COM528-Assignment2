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

@Controller
@RequestMapping("/")
public class MVCController {

    final static Logger LOG = LogManager.getLogger(MVCController.class);

    // this could be done with an autowired bean
    //private ShoppingService shoppingService = WebObjectFactory.getShoppingService();
    @Autowired
    ShoppingService shoppingService;

    // note that scope is session in configuration
    // so the shopping cart is unique for each web session
    @Autowired
    ShoppingCart shoppingCart;

    private User getSessionUser(HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            sessionUser = new User();
            sessionUser.setUsername("anonymous");
            sessionUser.setUserRole(UserRole.ANONYMOUS);
            session.setAttribute("sessionUser",sessionUser);
        }
        return sessionUser;
    }

    // this redirects calls to the root of our application to index.html
    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) {
        return "redirect:/index.html";
    }
    
    @RequestMapping(value = "/cart", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewCart(
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        // used to set tab selected
        model.addAttribute("selectedPage", "cart");

        String message = "";
        String errorMessage = "";


        List<OrderItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();

        // populate model with values
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);

        return "cart";
    }
    
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String viewCheckout(
            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

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

        return "checkout";
    }
    
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

        // used to set tab selected
        model.addAttribute("selectedPage", "checkout");

        String message = "";
        String errorMessage = "";
        
        //Validate
        CardValidationResult result = CardChecker.checkValidity(cardnumber);
        
        if(!result.getIsValid()){
            errorMessage = result.getMessage();
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
                    boolean purchased = shoppingService.purchaseItems(shoppingCart, sessionUser, card);
                    if(!purchased){
                        errorMessage = "Unable to purchase items. Please make sure you have entered your details correctly and that you have enough money in your account";
                    }
                    else{
                        message = "Successfully purchased items";
                    }
                }
                else{
                    errorMessage = stockMessage;
                }
            }
        }
        
        List<OrderItem> shoppingCartItems = shoppingCart.getShoppingCartItems();
        Double shoppingcartTotal = shoppingCart.getTotal();

        // populate model with values
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);

        return "checkout";
    }

    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public String viewHome(@RequestParam(name = "action", required = false) String action,
            @RequestParam(name = "itemName", required = false) String itemName,
            @RequestParam(name = "itemUUID", required = false) String itemUuid,            
            @RequestParam(name = "searchQuery", required = false) String searchQuery,

            Model model,
            HttpSession session) {

        // get sessionUser from session
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

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
            if (shoppingItem == null) {
                message = "cannot add unknown " + itemName + " to cart";
            } else {
                message = "adding " + itemName + " to cart price= " + shoppingItem.getPrice();
                shoppingCart.addItemToCart(shoppingItem);
            }
        } else if ("removeItemFromCart".equals(action)) {
            message = "removed " + itemName + " from cart";
            shoppingCart.removeItemFromCart(itemUuid);
        } else if ("search".equals(action) && searchQuery != null){
            message = "searched for: " +searchQuery; 
        }
        else {
            message = "unknown action=" + action;
        }
        
        
        List<ShoppingItem> availableItems = null;
        if ("search".equals(action) && searchQuery != null){
            availableItems = shoppingService.searchAvailableItems(searchQuery);  
        }
        else{
            availableItems = shoppingService.getAvailableItems();
        }
        

        List<OrderItem> shoppingCartItems = shoppingCart.getShoppingCartItems();

        Double shoppingcartTotal = shoppingCart.getTotal();

        // populate model with values
        model.addAttribute("availableItems", availableItems);
        model.addAttribute("shoppingCartItems", shoppingCartItems);
        model.addAttribute("shoppingcartTotal", shoppingcartTotal);
        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);

        return "home";
    }

    /*
     * Default exception handler, catches all exceptions, redirects to friendly
     * error page. Does not catch request mapping errors
     */
    @ExceptionHandler(Exception.class)
    public String myExceptionHandler(final Exception e, Model model, HttpServletRequest request) {
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
