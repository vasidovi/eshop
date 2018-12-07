/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.services;

import com.dovile.DAO.BasketLineDAO;
import com.dovile.DAO.ProductDAO;
import com.dovile.model.Basket;
import com.dovile.model.BasketLine;
import com.dovile.model.Product;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dovile
 */
@Service
@Transactional
public class BasketLineServices {
    
    
    @Autowired
    BasketLineDAO basketLineDAO;
    
    @Autowired
    ProductDAO productDAO;


    public BasketLine addItemsToBasket(Basket activeBasket, Product product) {
        
          BasketLine activeBasketLine = basketLineDAO.findByBasketAndProduct(activeBasket, product);       

        if (activeBasketLine != null) {
                   
            Integer totalAmount = activeBasketLine.getCount() + product.getCount();
            activeBasketLine.setCount(totalAmount);
            
            basketLineDAO.save(activeBasketLine);
            
        } else {
            
         activeBasketLine = getNewBasketLine(activeBasket, product);     
        }
        
        return activeBasketLine;

    }
    

    public BasketLine removeItemsFromBasket(Basket activeBasket, Product product) {
        
          Product  originalProduct = productDAO.getOne(product.getId());
          BasketLine activeBasketLine = basketLineDAO.findByBasketAndProduct(activeBasket, originalProduct);
          System.out.println("Show my product! " + product.toString());
          System.out.println("Show original product! " + originalProduct.toString());
               
        if (activeBasketLine.getCount() == product.getCount()) {                                         
            basketLineDAO.delete(activeBasketLine);
            activeBasketLine = null;
            
        } else {
            Integer remainsInBasket = activeBasketLine.getCount() - product.getCount();
            activeBasketLine.setCount(remainsInBasket);
            basketLineDAO.save(activeBasketLine);
        }        
        return activeBasketLine;
    }
    

    public BasketLine getNewBasketLine(Basket activeBasket, Product product) {
        
      BasketLine newBasketLine = new BasketLine();
      newBasketLine.setBasketId(activeBasket);
      newBasketLine.setProductId(product);
      newBasketLine.setCount(product.getCount());
      newBasketLine.setPrice(product.getPrice());
      newBasketLine.setName(product.getName());      
      basketLineDAO.save(newBasketLine);
      return newBasketLine;
    }

}















    
