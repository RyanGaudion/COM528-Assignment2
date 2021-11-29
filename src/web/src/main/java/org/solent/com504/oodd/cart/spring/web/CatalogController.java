/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.oodd.cart.spring.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.solent.com504.oodd.cart.dao.impl.ShoppingItemCatalogRepository;
import org.solent.com504.oodd.cart.model.dto.Address;
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

    @RequestMapping(value = {"/viewModifyItem"}, method = RequestMethod.POST)
    public String updateItem(
            @RequestParam(value = "name", required = true) String newName,            
            @RequestParam(value = "id", required = false) Long itemId,
            @RequestParam(value = "price", required = false) Double newPrice,
            @RequestParam(value = "quantity", required = false) Integer newQuantity,
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
            LOG.error("viewModifyItem Postcalled for unknown id=" + itemId);
            modifyItem = new ShoppingItem();
        }
        else{
            modifyItem = shoppingItem.get();
        }        

        // else update all other properties
        // only admin can update modifyUser role aand enabled
        if (UserRole.ADMINISTRATOR.equals(sessionUser.getUserRole())) {
            if(newName != null){
                modifyItem.setName(newName);
            }
            if(newPrice != null){
                modifyItem.setPrice(newPrice);
            }
            if(newQuantity != null){
                modifyItem.setQuantity(newQuantity);
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

            modifyItem = catalogRepo.save(modifyItem);
        }

        model.addAttribute("modifyItem", modifyItem);

        // add message if there are any 
        model.addAttribute("errorMessage", errorMessage);
        model.addAttribute("message", "Item " + modifyItem.getName()+ " updated successfully");

        model.addAttribute("selectedPage", "viewModifyItem");

        return "viewModifyItem";
    }
}