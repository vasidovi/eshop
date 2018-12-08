/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.model.requests;

import java.math.BigDecimal;

/**
 *
 * @author Dovile
 */
public class InvoiceRequestLine {
    
    private int count;
    private BigDecimal price;    
    private String name;      
    private Integer productId;

    public InvoiceRequestLine() {
    }

    public InvoiceRequestLine(int count, BigDecimal price, String name, Integer productId) {
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

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
    
    
    
    
    
    
}
