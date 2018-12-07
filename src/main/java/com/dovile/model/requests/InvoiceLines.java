/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.model.requests;

import com.dovile.model.InvoiceLine;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Dovile
 */
public class InvoiceLines {
    
    List<InvoiceLine> invoiceLines;

    public InvoiceLines(List<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    public InvoiceLines() {
    }
             
    public List<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceLines(List<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }
    
    
    
}