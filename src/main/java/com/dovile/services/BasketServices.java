/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.services;

import com.dovile.DAO.BasketDAO;
import com.dovile.DAO.BasketLineDAO;
import com.dovile.DAO.ProductDAO;
import com.dovile.DAO.UserDAO;
import com.dovile.model.Basket;
import com.dovile.model.BasketLine;
import com.dovile.model.Product;
import com.dovile.model.User;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dovile
 */
@Service
@Transactional
public class BasketServices {

    @Autowired
    BasketDAO basketDAO;

    @Autowired
    BasketLineDAO basketLineDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    ProductServices productServices;

    @Autowired
    BasketLineServices basketLineServices;

    @Autowired
    UserServices userServices;

    public Basket getOrCreateBasket(HttpServletRequest request) {

       Basket activeBasket = null;
       
       HttpSession session = request.getSession();
       String usernameStr = (String) session.getAttribute("username");
        
        User user = userDAO.findByUsername(usernameStr);

        if (user == null) {
            activeBasket = getOrCreateActiveBasket(request);
        } else {
            activeBasket = getOrCreateUserBasket(request, user);
        }
        return activeBasket;
    }

    public Basket getOrCreateActiveBasket(HttpServletRequest request) {

        Basket activeBasket =getActiveBasket(request);
        if (activeBasket == null) {
            activeBasket = getNewBasket(request);
        }
         return activeBasket;    


    }
    
     public Basket getActiveBasket(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer  basketId = (Integer) session.getAttribute("basketId");
        Basket activeBasket = null;        
        if ( basketId != null){
        activeBasket = basketDAO.getOne(basketId);
        }

        return activeBasket;    
     }
    

    public Basket getOrCreateUserBasket(HttpServletRequest request, User userIn) {
        
        Basket userBasket = getUserBasket(request, userIn);

        if (userBasket != null && userBasket.getPurchaseDate() == null ) {           
            return userBasket;
        } else {
            userBasket = getNewBasket(request);
            User user = userDAO.getOne(userIn.getId());
            userBasket.setUserId(user);
            basketDAO.saveAndFlush(userBasket);
        }

        return userBasket;
    }
    
    public Basket getUserBasket(HttpServletRequest request, User userIn) {
//        New impl.  think what and  why... 
        
//
//          HttpSession session = request.getSession();
//          Integer  basketId = null;
//          try {
//          basketId = (Integer) session.getAttribute("basketId");
//          } catch (NullPointerException e){
//              System.out.println("No user session atrribute");
//          }
//          Basket userBasket = null; 
//          
//          if (basketId != null){
//          try{
//            userBasket = basketDAO.getOne(basketId);
//          } catch (NullPointerException e){
//              System.out.println("No user basket with id " + basketId + " was found");
//          }
//          }

        // Old impl.   
        List<Basket> userBaskets = basketDAO.findByUser(userIn);
        
       if (userBaskets.size() > 0){
           for (Basket lastBasket : userBaskets){
           if (lastBasket.getPurchaseDate() == null){
               return lastBasket;
           }
           }
       }      
            return null;

    }

    public Basket mergeBaskets(Basket mergingBasket, Basket mainBasket) {
        List<BasketLine> mergingBasketLines = mergingBasket.getBasketLineList();
        if (mergingBasketLines.isEmpty()) {
            return mainBasket;
        } else {
            for (BasketLine line : mergingBasketLines) {
                Product p = productDAO.getOne(line.getProductId().getId());
                BasketLine activeBasketLine = basketLineDAO.findByBasketAndProduct(mainBasket, p);
                if (activeBasketLine != null) {
                    Integer totalAmount = activeBasketLine.getCount() + line.getCount();
                    activeBasketLine.setCount(totalAmount);
                    basketLineDAO.save(activeBasketLine);
                } else {

                    activeBasketLine = new BasketLine();
                    activeBasketLine.setBasketId(mainBasket);
                    activeBasketLine.setProductId(p);
                    activeBasketLine.setCount(line.getCount());
                    activeBasketLine.setPrice(p.getPrice());
                    activeBasketLine.setName(p.getName());
                    basketLineDAO.save(activeBasketLine);
                }
            }
            basketDAO.delete(mergingBasket);
            return mainBasket;
        }
    }
      

    public Integer getUserId(HttpServletRequest request) {
        Integer id = null;
        String userIdStr = request.getParameter("userId");
        if (userIdStr != null) {
            try {
                id = Integer.parseInt(userIdStr);
            } catch (NumberFormatException e) {
                System.out.println("Err: " + e);
            }
        }
        return id;
    }

    public Basket getNewBasket(HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        
        Basket basket = new Basket();
        basket.setSessionId(sessionId);
        basket.setCreateDate(new Date());
        basketDAO.saveAndFlush(basket);

        session.setAttribute("basketId", basket.getId());
        
        return basket;
    }

}
