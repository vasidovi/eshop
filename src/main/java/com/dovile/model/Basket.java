/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dovile
 */
@Entity
@Table(name = "basket")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Basket.findAll", query = "SELECT b FROM Basket b")
    , @NamedQuery(name = "Basket.findById", query = "SELECT b FROM Basket b WHERE b.id = :id")
    , @NamedQuery(name = "Basket.findBySessionId", query = "SELECT b FROM Basket b WHERE b.sessionId = :sessionId")
    , @NamedQuery(name = "Basket.findByCreateDate", query = "SELECT b FROM Basket b WHERE b.createDate = :createDate")
    , @NamedQuery(name = "Basket.findByPurchaseDate", query = "SELECT b FROM Basket b WHERE b.purchaseDate = :purchaseDate")})
public class Basket implements Serializable {

    private static final long serialVersionUID = 1L;
   
    private Integer id;
    private String sessionId;   
    private Date createDate;    
    private Date purchaseDate;       
    private User userId;    
    private List<BasketLine> basketLineList;

    public Basket() {
    }

    public Basket(Integer id) {
        this.id = id;
    }

    public Basket(Integer id, String sessionId, Date createDate) {
        this.id = id;
        this.sessionId = sessionId;
        this.createDate = createDate;
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
    @Size(min = 1, max = 45)
    @Column(name = "session_id")
    public String getSessionId() {
        return sessionId;
    } 

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
        
    @Column(name = "purchase_date")
    @Temporal(TemporalType.DATE)
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @XmlTransient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "basketId")
    public List<BasketLine> getBasketLineList() {
        return basketLineList;
    }

    public void setBasketLineList(List<BasketLine> basketLineList) {
        this.basketLineList = basketLineList;
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
        if (!(object instanceof Basket)) {
            return false;
        }
        Basket other = (Basket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dovile.model.Basket[ id=" + id + " ]";
    }
    
}
