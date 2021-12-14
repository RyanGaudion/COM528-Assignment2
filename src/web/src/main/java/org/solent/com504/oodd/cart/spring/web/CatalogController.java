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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.solent.com504.oodd.cart.dao.impl.ShoppingItemCatalogRepository;
import org.solent.com504.oodd.cart.model.dto.ShoppingItem;
import org.solent.com504.oodd.cart.model.dto.User;
import org.solent.com504.oodd.cart.model.dto.UserRole;
import static org.solent.com504.oodd.cart.spring.web.UserAndLoginController.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author rgaud
 */
@Controller
@RequestMapping("/")
public class CatalogController {
    
    private static final String UPLOAD_DIRECTORY ="/images";  
    
    @Autowired
    ShoppingItemCatalogRepository catalogRepo;
    
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
    
    /**
     * Get the list of all users from the DB
     * @param model
     * @param session
     * @return catalog page
     */
    @RequestMapping(value = {"/catalog"}, method = RequestMethod.GET)
    @Transactional
    public String users(Model model, HttpSession session) {
        String message = "";
        String errorMessage = "";

        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            errorMessage = "you must be an administrator to access users information";
            return "home";
        }

        List<ShoppingItem> catalogList = catalogRepo.findAll();

        model.addAttribute("catalogListSize", catalogList.size());
        model.addAttribute("catalogList", catalogList);
        model.addAttribute("selectedPage", "catalog");
        return "catalog";
    }
    
    /**
     * Gets the modify Item page
     * @param itemID ID of the item to edit
     * @param model
     * @param session
     * @return the view modify item page
     */
    @RequestMapping(value = {"/viewModifyItem"}, method = RequestMethod.GET)
    public String modifyItem(
            @RequestParam(value = "itemID", required = true) Long itemID,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        model.addAttribute("selectedPage", "viewModifyItem");

        LOG.debug("get viewModeifyItem called for id=" + itemID);

        // check secure access to modifyUser profile
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        
        Optional<ShoppingItem> shoppingItem = catalogRepo.findById(itemID);
        if (shoppingItem.isEmpty()) {
            LOG.error("viewModifyItem called for unknown id=" + itemID);
            return ("home");
        }

        ShoppingItem modifyItem = shoppingItem.get();
        model.addAttribute("modifyItem", modifyItem);

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        return "viewModifyItem";
    }
    
    /**
     * Gets an empty viewmodifyItem page - to be able to create a new item
     * @param model
     * @param session
     * @return the viewmModifyItem page
     */
    @RequestMapping(value = {"/createItem"}, method = RequestMethod.GET)
    public String createItem(
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        model.addAttribute("selectedPage", "createItem");

        LOG.debug("get create item page");

        // check secure access to modifyUser profile
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        model.addAttribute("message", message);
        model.addAttribute("errorMessage", errorMessage);
        return "viewModifyItem";
    }

    /**
     * POST request to update or create a catalog item
     * @param newName name from the page form
     * @param action either empty or delete
     * @param itemId ID of the Item if editing
     * @param inputPrice price from the page form
     * @param inputQuantity quantity from the page form
     * @param newCategory category from the view modify item page
     * @param deactivated whether the item is deactivated or not 
     * @param file image file if uploaded
     * @param model
     * @param session
     * @return the viewmodifyitem pag
     */
    @RequestMapping(value = {"/viewModifyItem"}, method = RequestMethod.POST)
    public String updateItem(
            @RequestParam(value = "name", required = true) String newName,            
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "id", required = false) Long itemId,
            @RequestParam(value = "price", required = false) String inputPrice,
            @RequestParam(value = "quantity", required = false) String inputQuantity,             
            @RequestParam(value = "category", required = false) String newCategory,
            @RequestParam(value = "deactivated", required = false) Boolean deactivated,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Model model,
            HttpSession session) {
        String message = "";
        String errorMessage = "";

        LOG.debug("post updateItem called for id=" + itemId);

        
        
        // security check if party is allowed to access or modify this party
        User sessionUser = getSessionUser(session);
        model.addAttribute("sessionUser", sessionUser);

        if (!UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            errorMessage = "security viewModifyItem called by non-admin user" + sessionUser.getUsername();
            model.addAttribute("errorMessage", errorMessage);
            LOG.warn(errorMessage);
            return ("home");
        }

        Optional<ShoppingItem> shoppingItem = null;
        if(itemId != null){
            shoppingItem = catalogRepo.findById(itemId);
        }
        
        
        ShoppingItem modifyItem;
        if (shoppingItem == null || shoppingItem.isEmpty()) {
            LOG.error("viewModifyItem Post called for unknown id=" + itemId);
            modifyItem = new ShoppingItem();
        }
        else{
            modifyItem = shoppingItem.get();
        }        
        
        
        
        //Delete item
        if(action != null && action.equals("delete")){
            if (shoppingItem == null || shoppingItem.isEmpty()) {
                errorMessage = "Unable to delete item - please try setting the item to de-activated instead";
            }
            else{
                try{
                    message = "Successfully deleted item: " + shoppingItem.get().getName();
                    catalogRepo.delete(shoppingItem.get());
                    model.addAttribute("message", message);
                    return ("redirect:/catalog");
                }
                catch(Exception ex){
                    LOG.error(ex);
                    message = "";
                    errorMessage = "Unable to delete item - this may be due to the fact the item is included in a previous order - please try setting the item to de-activated instead";
                }
            }
        }
        else{
        List<ShoppingItem> existingItems = catalogRepo.findByNameIgnoreCase(newName);
        //If adding new item - check name doesn't already exist
        Boolean invalid = false;
        if(modifyItem.getId() == null && existingItems.size() > 0){
            invalid = true;
            errorMessage = "Item with that name already exists";
        }
        //If editing then check name doesn't exist and match isn't with current item
        else{
            for (ShoppingItem item : existingItems){
                if(!(item.getId().equals(modifyItem.getId()))){
                    invalid = true;
                    errorMessage = "Item with that name already exists";
                }
            }
        }
        
        // else update all other properties
        // only admin can update modifyUser role aand enabled
        if (UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole()) && !invalid) {
            
            if(newName != null && !(newName.equals(""))){
                modifyItem.setName(newName);
            }
            else{
                invalid = true;
                errorMessage = "A name must be set for the item";
            }
            if(inputPrice != null){
                try{
                    double newPrice = Double.parseDouble(inputPrice);
                    modifyItem.setPrice(newPrice);
                }
                catch(NumberFormatException ex){
                    invalid = true;
                    errorMessage = "A price must in the format of a number";
                }
            }
            else{
                invalid = true;
                errorMessage = "A price must be set for the item";
            }
            if(inputQuantity != null){
                try{
                    Integer newQuantity = Integer.parseInt(inputQuantity);
                    modifyItem.setQuantity(newQuantity);
                }
                catch(NumberFormatException ex){
                    invalid = true;
                    errorMessage = "A quantity must in the format of a whole number";
                }
            }
            else{
                invalid = true;
                errorMessage = "A quantity must be set for the item";
            }
            if(deactivated != null){
                modifyItem.setDeactivated(deactivated);
            }
            if(newCategory != null){
                modifyItem.setCategory(newCategory);
            }
            if (file.isEmpty()) {
                LOG.warn("file is empty");
            }
            else{
                LOG.info("file is not empty");
                ServletContext context = session.getServletContext();  
                String path = context.getRealPath(UPLOAD_DIRECTORY);  
                String filename = file.getOriginalFilename();  

                LOG.info(path+ File.separator+filename);        

                byte[] bytes;  
                try {
                    bytes = file.getBytes();
                    BufferedOutputStream stream =new BufferedOutputStream(new FileOutputStream(  
                         new File(path + File.separator + filename)));  
                    stream.write(bytes);  
                    stream.flush();  
                    stream.close();  
                    modifyItem.setFilename(filename);
                    LOG.info(path + File.separator + filename);
                    } catch (IOException ex) {
                    LOG.error("failed to upload image");
                }
            }
            if(!invalid){
                modifyItem = catalogRepo.save(modifyItem);
            }
        }

        
        }
        model.addAttribute("modifyItem", modifyItem);

        
        // add message if there are any 
        model.addAttribute("errorMessage", errorMessage);
        if(errorMessage.equals("")){
            model.addAttribute("message", "Item " + modifyItem.getName()+ " updated successfully");
        }

        model.addAttribute("selectedPage", "viewModifyItem");

        return "viewModifyItem";
        
    }
}
