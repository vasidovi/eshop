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
import com.dovile.DAO.BasketDAO;
import com.dovile.DAO.BasketLineDAO;
import com.dovile.DAO.InvoiceDAO;
import com.dovile.DAO.ProductDAO;
import com.dovile.model.Basket;
import com.dovile.model.Invoice;
import com.dovile.model.Product;
import com.dovile.model.requests.InvoiceRequestLine;
import com.dovile.model.requests.InvoiceRequest;
import com.dovile.model.requests.ProductEditRequest;
import com.dovile.services.InvoiceLineServices;
import com.dovile.services.InvoiceServices;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private InvoiceServices invoiceServices;

    @Autowired
    private InvoiceLineServices invoiceLineServices;

    @Autowired
    private InvoiceDAO invoiceDAO;

    @Autowired
    private BasketDAO basketDAO;

    @Autowired
    private BasketLineDAO basketLineDAO;

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
        
     @GetMapping("/review_baskets")
    public String viewFilteredBasketsPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Boolean validAccess = (Boolean) session.getAttribute("admin");

//        if (validAccess) {
        model.addAttribute("user", "Administrator");
        
        String idStr = request.getParameter("id");
        Integer id  = null;
        
        try{
            id = Integer.parseInt(idStr);
        } catch(Exception e){
        // suppress warning
        }
                
       if (id != null ){
                  
          Optional<Basket> basket = basketDAO.findById(id);
        if (basket.isPresent()) {
            model.addAttribute("basketLines", basketLineDAO.findByBasket(basket.get()));
            model.addAttribute("id", idStr);
        }
       } else{
           model.addAttribute("id", "null");
       }
       
        Boolean hasUser;
        Boolean isPurchased;
             
        String bought = request.getParameter("isPurchased");
        if (bought != null && bought.equals("true")){
            isPurchased = true;
        } else{
            isPurchased = false;
        }
               
        String user =request.getParameter("hasUser");
        if (user != null && user.equals("true")){
         hasUser = true;
        } else{
            hasUser = false;
        }
        model.addAttribute("hasUser", hasUser);
        model.addAttribute("isPurchased", isPurchased);

        List<Basket> baskets= basketDAO.findByUserOrPurchaseStatus(hasUser, isPurchased);
        model.addAttribute("baskets", baskets);

        return "reviewbaskets";
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
