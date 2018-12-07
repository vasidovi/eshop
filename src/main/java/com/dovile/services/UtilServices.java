/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.services;

import com.dovile.DAO.BasketDAO;
import com.dovile.DAO.BasketLineDAO;
import com.dovile.DAO.ProductDAO;
import com.dovile.model.Basket;
import com.dovile.model.BasketLine;
import com.dovile.model.Product;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dovile
 */
@Service
@Transactional
public class UtilServices {

    @Autowired
    BasketDAO basketDAO;

    @Autowired
    BasketLineDAO basketLineDAO;

    @Autowired
    ProductDAO productDAO;

    public void retrieveClosedSessionProducts(HttpServletRequest request) {
        

        List<Basket> allBaskets = basketDAO.findAll();

        for (Basket basket : allBaskets) {
            // If basket was not bought ant is not from current session; 
            if ( basket.getPurchaseDate() == null && !basket.getSessionId().equals(request.getSession().getId()) ) {
                List<BasketLine> basketLines = basketLineDAO.findAll();

                for (BasketLine line : basketLines) {
                    Integer reservedProductCount = line.getCount();
                    Product product = line.getProductId();
                    

                    Integer totalProductCount = product.getCount();
                    
                    System.out.println("Session id :" + basket.getSessionId());
                    System.out.println("Product id :" + line.getProductId());
                    System.out.println("Reserved count :" + line.getCount());
                    System.out.println("Product count :" + product.getCount());
                    System.out.println("-----------------------------");
                    

//                    if (totalProductCount > product.getAvailable() ) {
//                        product.setAvailable(product.getAvailable() + reservedProductCount);
//                        productDAO.save(product);
                    }
                
            }
        }
    }
 }

