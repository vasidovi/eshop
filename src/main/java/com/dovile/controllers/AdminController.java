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
import com.dovile.DAO.ProductDAO;
import com.dovile.model.Invoice;
import com.dovile.model.Product;
import com.dovile.model.requests.InvoiceRequestLine;
import com.dovile.model.requests.InvoiceRequest;
import com.dovile.model.requests.ProductEditRequest;
import com.dovile.services.InvoiceLineServices;
import com.dovile.services.InvoiceServices;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private InvoiceServices invoiceServices;
    
    @Autowired
    private InvoiceLineServices invoiceLineServices;

    @Autowired
    private InvoiceDAO invoiceDAO;

    @RequestMapping("editProductDetail")
    public String editProductDetail(HttpServletRequest request,
            @ModelAttribute("editProductDetail") ProductEditRequest productModel) {

        Product p = productDAO.getOne(productModel.getId());

        p.setName(productModel.getName());
        p.setPrice(productModel.getPrice());
        productDAO.save(p);

        return "redirect:/admin_page";
    }

    @GetMapping("/admin_page")
    public String adminPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Boolean validAccess = (Boolean) session.getAttribute("admin");

        if (validAccess) {
            model.addAttribute("user", "Administrator");
            model.addAttribute("products", productDAO.findAll());
            return "admin";
        } else {
            return "index";
        }
    }

    @GetMapping("/newShipment")
    public String registerNewShipmentv2(HttpServletRequest request,
            Model model
    ) {
        HttpSession session = request.getSession();
        Boolean validAccess = (Boolean) session.getAttribute("admin");

        if (validAccess) {
            model.addAttribute("products", productDAO.findAll());
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
            HttpServletRequest request,
             @ModelAttribute("invoice") InvoiceRequest invoiceRequest
    ) {
        Invoice invoice = invoiceServices.saveInvoice(invoiceRequest);

        List<InvoiceRequestLine> requestLines = invoiceRequest.getInvoiceLineList();        
        Invoice registeredInvoice = null;
        
        List<Invoice> invoices = invoiceDAO.findByDateAndSupplier(invoice.getRecieveDate(), invoice.getSupplier()); 
       
        if (invoices != null) {
            registeredInvoice = invoices.get(invoices.size() - 1);

            for (InvoiceRequestLine requestLine : requestLines) {                
                invoiceLineServices.saveInvoiceLine(requestLine, registeredInvoice);               
            }
        }
        return "redirect:/admin_page";
    }

}
