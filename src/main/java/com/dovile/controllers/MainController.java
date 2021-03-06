/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.controllers;

//import com.dovile.DAO.BasketDAO;
import com.dovile.DAO.BasketDAO;
import com.dovile.DAO.BasketLineDAO;
import com.dovile.DAO.ProductDAO;
import com.dovile.DAO.UserDAO;
import com.dovile.services.BasketServices;
import com.dovile.model.Basket;
import com.dovile.model.BasketLine;
import com.dovile.model.Product;
import com.dovile.model.User;
import com.dovile.model.requests.UserLoginRequest;
import com.dovile.services.BasketLineServices;
import com.dovile.services.ProductServices;
import com.dovile.services.UserServices;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Dovile
 */
@Controller
//@RequestMapping("/")
public class MainController {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private BasketDAO basketDAO;

    @Autowired
    private BasketLineDAO basketLineDAO;
    
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private BasketServices basketServices;

    @Autowired
    private BasketLineServices basketLineServices;

    @Autowired
    private ProductServices productServices;

    @Autowired
    private UserServices userServices;

    @GetMapping("/")
    @Transactional
    public String getMainPage(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        String usernameStr = (String) session.getAttribute("username");

        Basket activeBasket;
        List<BasketLine> lines = new ArrayList<>();

        if (usernameStr == null) {
            activeBasket = basketServices.getActiveBasket(request);
        } else {
            activeBasket = basketServices.getUserBasket(request, userDAO.findByUsername(usernameStr));
        }

        if (activeBasket != null) {
            lines = basketLineDAO.findByBasket(activeBasket);
        }

        model.addAttribute("basketCount", lines.size());
        model.addAttribute("user", usernameStr);
        model.addAttribute("products", productDAO.findAll());

        return "index";
    }

    @GetMapping("/basket")
    @Transactional
    public String getBasketContents(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        String usernameStr = (String) session.getAttribute("username");

        Basket activeBasket;
        List<BasketLine> lines = new ArrayList<>();

        if (usernameStr == null) {
            activeBasket = basketServices.getActiveBasket(request);
        } else {
            activeBasket = basketServices.getUserBasket(request, userDAO.findByUsername(usernameStr));
        }

        if (activeBasket != null) {
            lines = basketLineDAO.findByBasket(activeBasket);
        }

        model.addAttribute("user", usernameStr);
        model.addAttribute("basketLines", lines);
        return "basket";
    }

    @PostMapping("/to_basket")
    @Transactional
    public String addToBasket(HttpServletRequest request, @ModelAttribute("product") Product product) {
        Basket activeBasket = basketServices.getOrCreateBasket(request);
        basketLineServices.addItemsToBasket(activeBasket, product);
        return "redirect:/";
    }

    @PostMapping("/from_basket")
    @Transactional
    public String removeFromBasket(HttpServletRequest request, @ModelAttribute("product") Product product) {

        Basket activeBasket = basketServices.getOrCreateBasket(request);
        basketLineServices.removeItemsFromBasket(activeBasket, product);
        List<BasketLine> lines = basketLineDAO.findByBasket(activeBasket);
        if (lines == null) {
            basketDAO.delete(activeBasket);
        }
        return "redirect:/basket";
    }

    @GetMapping("/buy")
    @Transactional
    public String buy(HttpServletRequest request) {

        Basket activeBasket = basketServices.getOrCreateBasket(request);

        if (activeBasket.getUserId() != null) {
            List<BasketLine> basketLines = basketLineDAO.findByBasket(activeBasket);

            for (BasketLine line : basketLines) {
                Integer reserved = line.getCount();
                Product product = line.getProductId();
                Integer productId = product.getId();
                Product p = productServices.reduceProductTotalCountBy(productId, reserved);

                activeBasket.setPurchaseDate(new Date());
                basketDAO.save(activeBasket);
            }
            return "redirect:/";

        } else {
            System.out.println("Trying to buy could not find userbasket" + activeBasket.getId() + "user basket "  + activeBasket.getUserId());
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }
    
      @GetMapping(value = {"/seed"})
    public String seed(){
        
        System.out.println(encoder.encode("user"));
        return "redirect:/";
        
    }

    @RequestMapping("/login")

    public String login(HttpServletRequest request,
            HttpServletResponse response,
            Model model,
            @ModelAttribute("userLogin") UserLoginRequest userModel) {

        Boolean validLogin = userServices.validateLogin(userModel.getUsername(), userModel.getPassword());
           
        
          
        if (validLogin) {

            User user = userDAO.findByUsername(userModel.getUsername());
               
            if (user.getAdmin() != null) {
                return "redirect:/admin";
            }
            
            Basket sessionBasket = basketServices.getActiveBasket(request);
            Basket userBasket = basketServices.getUserBasket(request, user);

            if (sessionBasket != null && userBasket != null) {
                
                basketServices.mergeBaskets(sessionBasket, userBasket);
            } else if (sessionBasket != null) {
                
                sessionBasket.setUserId(user);
                basketDAO.save(sessionBasket);
            }

            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());

            return "redirect:/";
        } // if login invalid
        else {
            model.addAttribute("isInvalid", "wrong username and password combination");
        }
        // create counter for max login attempts ?  
        return "/login";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Basket activeBasket = basketServices.getOrCreateActiveBasket(request);
        List<BasketLine> basketLines = basketLineDAO.findByBasket(activeBasket);
        for (BasketLine line : basketLines) {
            Integer reserved = line.getCount();
            Integer productId = line.getProductId().getId();
        }

        session.invalidate();
        return "redirect:/";
    }

}
