/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.model.requests;

import com.dovile.model.Invoice;
import com.dovile.model.Product;
import java.math.BigDecimal;

/**
 *
 * @author Dovile
 */
public class InvoiceLineRequest {
    
    private int count;
    private BigDecimal price;    
    private String name;      
    private int productId;

    public InvoiceLineRequest() {
    }

    public InvoiceLineRequest(int count, BigDecimal price, String name, int productId) {
        this.count = count;
        this.price = price;
        this.name = name;
        this.productId = productId;
    }
               
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    
    
    
    
    
    
}
