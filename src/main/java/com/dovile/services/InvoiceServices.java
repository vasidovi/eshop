/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.services;

import com.dovile.DAO.InvoiceDAO;
import com.dovile.model.Invoice;
import com.dovile.model.requests.InvoiceRequest;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dovile
 */
@Service
@Transactional
public class InvoiceServices {
    
        @Autowired
        private InvoiceDAO invoiceDAO;
    
        public Invoice saveInvoice(InvoiceRequest invoiceRequest) {
        
        Date invoiceDate =  invoiceRequest.getRecieveDate();
         String supplier = invoiceRequest.getSupplier();
         
         Invoice invoice = new Invoice();
         invoice.setSupplier(supplier);
         invoice.setRecieveDate(invoiceDate);
         invoiceDAO.saveAndFlush(invoice);
        
        return invoice;
    }
    
    
    
    
}
