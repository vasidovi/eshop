/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.controllers;

/**
 *
 * @author Dovile
 */
import com.dovile.DAO.InvoiceDAO;
import com.dovile.DAO.InvoiceLineDAO;
import com.dovile.DAO.ProductDAO;
import com.dovile.model.Invoice;
import com.dovile.model.InvoiceLine;
import com.dovile.model.Product;
import com.dovile.model.requests.InvoiceLineRequest;
import com.dovile.model.requests.InvoiceRequest;
import com.dovile.model.requests.ProductEditRequest;
import com.dovile.services.ProductServices;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private ProductServices productServices;
    
    @Autowired
    private InvoiceLineDAO invoiceLineDAO;
    
    @Autowired
    private InvoiceDAO invoiceDAO;
           
    
    @RequestMapping("editProductDetail")
    public String editProductDetail(HttpServletRequest request,
            @ModelAttribute("editProductDetail") ProductEditRequest productModel) {
        
       Product p = productDAO.getOne(productModel.getId());          
       
       p.setName(productModel.getName());
       
       p.setPrice(productModel.getPrice());
       p.setAvailable(productModel.getAvailable());         
       productDAO.save(p);
       
      return "redirect:/admin_page";
    }
    
    @GetMapping("/admin_page")
       public String adminPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Boolean validAccess = (Boolean) session.getAttribute("admin");

    if (validAccess){
                model.addAttribute("user", "Administrator");
                model.addAttribute("products", productDAO.findAll());                 
            return "admin";        
        } else {
         return "index";   
        }
}
         
    // make into post mapping
    @GetMapping("/newGoods/{lines}")
     public String registerNewShipmentv2(HttpServletRequest request,
             Model model
            ,@PathVariable Integer lines
     ) {
          HttpSession session = request.getSession();
        Boolean validAccess = (Boolean) session.getAttribute("admin");
        
        if (validAccess){
                            model.addAttribute("products", productDAO.findAll());

            model.addAttribute("lines", lines);
            return "shipmentform";        
        } else {
         return "index";   
        }
    
     }
          
     @InitBinder
	public void dataBinding(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		binder.registerCustomEditor(Date.class, "recieveDate", new CustomDateEditor(dateFormat, true));
	}
     
      @PostMapping("/registerInvoice")
     public String registerNewInvoice(
             HttpServletRequest request
             ,@ModelAttribute("invoice") InvoiceRequest invoiceRequest
     ){
         
         Date invoiceDate =  invoiceRequest.getRecieveDate();
         String supplier = invoiceRequest.getSupplier();
         
         Invoice invoice = new Invoice();
         invoice.setSupplier(supplier);
         invoice.setRecieveDate(invoiceDate);
         
         List<InvoiceLineRequest> requestLines =  invoiceRequest.getInvoiceLineList();
         List<InvoiceLine> lines = new ArrayList<>();
                   
       invoiceDAO.saveAndFlush(invoice);        
       
       List<Invoice> invoices = invoiceDAO.findByDateAndSupplier(invoiceDate, supplier);
       Invoice registeredInvoice = null;
       
       if (invoices != null){
           registeredInvoice = invoices.get(invoices.size()-1);   

          for (InvoiceLineRequest requestLine : requestLines){
                             
              InvoiceLine line = new InvoiceLine();
              
              line.setInvoiceId(registeredInvoice);
              line.setCount(requestLine.getCount());
              line.setName(requestLine.getName());
              line.setPrice(requestLine.getPrice());
             
             Integer productId = null;
             
             try{
             productId = requestLine.getProductId();
             } catch(NullPointerException e){
             // suppress warning
             }
             
              System.out.println("!!!!!!!!" + productId);
             Product product = null;
             
             
             if (productId != null)
             product = productDAO.getOne(productId);
             
 
              if (product == null){
                product = productServices.newProduct(line);
              } else {
                product.setCount(product.getCount() + requestLine.getCount());
                product.setAvailable(product.getAvailable() + requestLine.getCount());
                productDAO.save(product);
              }
            
              line.setProductId(product);

              invoiceLineDAO.save(line);                             
          }                  
       }
          return "redirect:/admin_page";
     }
     
     }
