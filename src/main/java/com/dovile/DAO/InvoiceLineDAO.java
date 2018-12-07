/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.DAO;

import com.dovile.model.InvoiceLine;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Nikanoras
 */
@Transactional

public interface InvoiceLineDAO extends JpaRepository<InvoiceLine, Integer> {
    
}
