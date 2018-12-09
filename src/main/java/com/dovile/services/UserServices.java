/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.services;

import com.dovile.DAO.RoleDAO;
import com.dovile.DAO.UserDAO;
import com.dovile.model.Role;
import com.dovile.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dovile
 */

@Service
public class UserServices {
    
    
    @Autowired
    UserDAO userDAO;
    @Autowired
    RoleDAO roleDAO;
     
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    public boolean validateLogin(String username, String password){
        User user = userDAO.findByUsername(username);
        password.trim();
        System.out.println("My unencoded password" + password);
        if (user != null){            
            if (user.getPassword().equals(encoder.encode(password)))            
            return true;
        }
        System.out.println("User password from DB");
        System.out.println(user.getPassword());
        System.out.println("Encoded password");
         System.out.println(encoder.encode(password));

        return false;
        }
    
    public void registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleDAO.findByRole("USER"));       
        user.setRoleList(roleList);
        userDAO.save(user);
    }
    
 
}

