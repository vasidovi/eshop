/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.services;

import com.dovile.DAO.UserDAO;
import com.dovile.model.User;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dovile
 */

@Service
public class UserServices {
    
    
     @Autowired
     UserDAO userDAO;
    
    public boolean validateLogin(String username, String password){
        User user = userDAO.findByUsername(username);
        if (user != null){
            if (user.getPassword().equals(password))            
            return true;
        }
        return false;
        }
    
//    public User getUserFormCookie(HttpServletRequest request){
//        String  cookieName ="username";
//        String  usernameStr = "";
//        
//        try {
//        for (Cookie c : request.getCookies()){
//            if (c.getName().equals(cookieName)){
//                usernameStr = c.getValue();                 
//               break;
//            }                
//        }
//        } catch (NullPointerException e){
//            return null;
//        }
//        
//        User user = null;
//        if (!usernameStr.isEmpty()){
//            user = userDAO.findByUsername(usernameStr);
//        }
//        return user;
//    }       
 
}

