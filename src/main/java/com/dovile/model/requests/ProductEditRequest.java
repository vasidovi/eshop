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
public class ProductEditRequest {

    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer available;

    public ProductEditRequest(Integer id, String name, BigDecimal price, Integer available) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
    }

    public ProductEditRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "ProductEditRequest{" + "id=" + id + ", name=" + name + ", price=" + price + ", available=" + available + '}';
    }
    
   

}
