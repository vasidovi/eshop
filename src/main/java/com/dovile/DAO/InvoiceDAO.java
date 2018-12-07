/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.DAO;

import com.dovile.model.Invoice;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nikanoras
 */
@Transactional

public interface InvoiceDAO extends JpaRepository<Invoice, Integer> {
    
     @Query("Select i from Invoice i where i.recieveDate=:recieveDate and i.supplier=:supplier")
    public List<Invoice> findByDateAndSupplier(@Param("recieveDate") Date recieveDate,
            @Param("supplier") String supplier); 
    
}
