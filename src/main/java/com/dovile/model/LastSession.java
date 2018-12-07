/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "last_session")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LastSession.findAll", query = "SELECT l FROM LastSession l")
    , @NamedQuery(name = "LastSession.findById", query = "SELECT l FROM LastSession l WHERE l.id = :id")
    , @NamedQuery(name = "LastSession.findBySessionId", query = "SELECT l FROM LastSession l WHERE l.sessionId = :sessionId")})
public class LastSession implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "session_id")
    private String sessionId;

    public LastSession() {
    }

    public LastSession(Integer id) {
        this.id = id;
    }

    public LastSession(Integer id, String sessionId) {
        this.id = id;
        this.sessionId = sessionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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
        if (!(object instanceof LastSession)) {
            return false;
        }
        LastSession other = (LastSession) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dovile.model.LastSession[ id=" + id + " ]";
    }
    
}
