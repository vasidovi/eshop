/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dovile
 */
@Entity
@Table(name = "basket_line")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BasketLine.findAll", query = "SELECT b FROM BasketLine b")
    , @NamedQuery(name = "BasketLine.findById", query = "SELECT b FROM BasketLine b WHERE b.id = :id")
    , @NamedQuery(name = "BasketLine.findByName", query = "SELECT b FROM BasketLine b WHERE b.name = :name")
    , @NamedQuery(name = "BasketLine.findByPrice", query = "SELECT b FROM BasketLine b WHERE b.price = :price")
    , @NamedQuery(name = "BasketLine.findByCount", query = "SELECT b FROM BasketLine b WHERE b.count = :count")})
public class BasketLine implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String name;    
    private BigDecimal price;
    private int count;
    private Basket basketId;    
    private Product productId;

    public BasketLine() {
    }

    public BasketLine(Integer id) {
        this.id = id;
    }

    public BasketLine(Integer id, BigDecimal price, int count) {
        this.id = id;
        this.price = price;
        this.count = count;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Size(max = 45)
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    public Basket getBasketId() {
        return basketId;
    }

    public void setBasketId(Basket basketId) {
        this.basketId = basketId;
    }
    
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BasketLine)) {
            return false;
        }
        BasketLine other = (BasketLine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dovile.model.BasketLine[ id=" + id + " ]";
    }
    
}
