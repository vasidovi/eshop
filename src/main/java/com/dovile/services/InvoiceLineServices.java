/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.services;

import com.dovile.DAO.InvoiceLineDAO;
import com.dovile.DAO.ProductDAO;
import com.dovile.model.Invoice;
import com.dovile.model.InvoiceLine;
import com.dovile.model.Product;
import com.dovile.model.requests.InvoiceRequest;
import com.dovile.model.requests.InvoiceRequestLine;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dovile
 */
@Service
@Transactional
public class InvoiceLineServices {
    
    @Autowired
    ProductDAO productDAO;
    
    @Autowired
    private InvoiceLineDAO invoiceLineDAO;
    
    
    @Autowired
    private ProductServices productServices;

    
    public InvoiceLine saveInvoiceLine(InvoiceRequestLine requestLine, Invoice registeredInvoice) {
        InvoiceLine line = new InvoiceLine();

                line.setInvoiceId(registeredInvoice);
                line.setCount(requestLine.getCount());
                line.setName(requestLine.getName());
                line.setPrice(requestLine.getPrice());
                Integer productId = null;

                try {
                    productId = requestLine.getProductId();
                } catch (NullPointerException e) {
                    // suppress warning
                }

                Product product = null;
                
                if (productId != null) {
                    product = productDAO.getOne(productId);
                }

                if (product == null) {
                    product = productServices.newProduct(line);
                } else {
                    product.setCount(product.getCount() + requestLine.getCount());
                    productDAO.save(product);
                }

                line.setProductId(product);

                invoiceLineDAO.save(line);
                
                return line;
    }
    
}
