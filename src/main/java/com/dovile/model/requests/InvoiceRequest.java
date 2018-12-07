/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.model.requests;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dovile
 */
public class InvoiceRequest {
    
    private Date recieveDate;    
    private String supplier;        
    List<InvoiceLineRequest> invoiceLines;

    public Date getRecieveDate() {
        return recieveDate;
    }

    public void setRecieveDate(Date recieveDate) {
        this.recieveDate = recieveDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public InvoiceRequest(Date recieveDate, String supplier, List<InvoiceLineRequest> invoiceLines) {
        this.recieveDate = recieveDate;
        this.supplier = supplier;
        this.invoiceLines = invoiceLines;
    }
       
    public InvoiceRequest() {
    }
             
    public List<InvoiceLineRequest> getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceLines(List<InvoiceLineRequest> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }
    
    
    
}