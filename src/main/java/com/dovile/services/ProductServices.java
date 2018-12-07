/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.services;

import com.dovile.DAO.ProductDAO;
import com.dovile.model.BasketLine;
import com.dovile.model.InvoiceLine;
import com.dovile.model.Product;
import java.math.BigDecimal;
import java.util.Optional;
import javax.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dovile
 */
@Service
public class ProductServices {
    
    
    @Autowired
    ProductDAO productDAO;
    
    private static final Log log = LogFactory.getLog(ProductServices.class);
    private static final Integer PRICE_ADDITION_PERCENT = 20;
    
    @Transactional      
    public Product reduceProductTotalCountBy(Integer productId, Integer number){
                
        Product product = selectProduct(productId);
        Integer totalProductCount = product.getCount();        
        if (totalProductCount >= number) {
        product.setCount(totalProductCount - number);        
        productDAO.save(product);        
        } else {
            log.error("trying to reduce product by " + number + " , when only " + totalProductCount +" exists");
        }
        return product;
      }
    @Transactional
     public Product reduceProductAvailableCountBy(Integer productId, Integer number){
        
       Product product = selectProduct(productId);
        Integer available = product.getAvailable();        
        if (available >= number) {
        product.setAvailable(available  - number);
        productDAO.save(product);        
        } else {
            log.error("trying to reduce available product by " + number + " , when only " + available +" is available");
        }
        return product;
    }
     
    @Transactional 
    public Product increaseProductAvailableCountBy(Integer productId, Integer number){
        
       Product product = selectProduct(productId);
       Integer available = product.getAvailable();        

       product.setAvailable(available  + number);
       productDAO.save(product);        
    
        return product;
    } 
    
       //not used ???
        public Product convertBasketLineIntoProductObj(BasketLine bl){
     
        Product p = new Product();
        p.setName(bl.getName());
        p.setPrice(bl.getPrice());
        p.setCount(bl.getCount());
        p.setAvailable(bl.getCount());
        return p;
    }
    
    @Transactional
    public Product newProduct(InvoiceLine invoiceLine){
        
        Product product = new Product();
       
        product.setCount(invoiceLine.getCount());
        product.setAvailable(invoiceLine.getCount());
        product.setName(invoiceLine.getName());
        if (invoiceLine.getPrice() != null){
       
        BigDecimal invoicePrice = invoiceLine.getPrice();
       
        BigDecimal priceAddition = invoicePrice
                .multiply(new BigDecimal(PRICE_ADDITION_PERCENT))
                .divide(new BigDecimal("100"));
        BigDecimal defaultPrice = invoicePrice.add(priceAddition);
        
                 product.setPrice(defaultPrice);
        } else {
            product.setPrice( BigDecimal.ZERO);
        }
        
        productDAO.save(product);
        
        return product;
    }    
        
        
        
    
     @Transactional
       public Product selectProduct(Integer productId){
        
        Optional<Product> productOpt = productDAO.findById(productId);
        Product product = null;
        
        if (productOpt.isPresent()){
            product = productOpt.get();
        } else {
            log.error("no product found");
        }
        return product;
       }
          
}















    
