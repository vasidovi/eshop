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
@Table(name = "invoice_line")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "InvoiceLine.findAll", query = "SELECT i FROM InvoiceLine i")
    , @NamedQuery(name = "InvoiceLine.findById", query = "SELECT i FROM InvoiceLine i WHERE i.id = :id")
    , @NamedQuery(name = "InvoiceLine.findByCount", query = "SELECT i FROM InvoiceLine i WHERE i.count = :count")
    , @NamedQuery(name = "InvoiceLine.findByPrice", query = "SELECT i FROM InvoiceLine i WHERE i.price = :price")
    , @NamedQuery(name = "InvoiceLine.findByName", query = "SELECT i FROM InvoiceLine i WHERE i.name = :name")})
public class InvoiceLine implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer id;
    private int count;
    private BigDecimal price;    
    private String name;      
    private Invoice invoiceId;    
    private Product productId;

    public InvoiceLine() {
    }

    public InvoiceLine(Integer id) {
        this.id = id;
    }

    public InvoiceLine(Integer id, int count) {
        this.id = id;
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
    
     
    @Basic(optional = false)
    @NotNull
    @Column(name = "count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    @Size(max = 45)
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    @ManyToOne(optional = false)  
    public Invoice getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
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
        if (!(object instanceof InvoiceLine)) {
            return false;
        }
        InvoiceLine other = (InvoiceLine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dovile.model.InvoiceLine[ id=" + id + " ]";
    }
    
}
