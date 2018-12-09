/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.config;


import com.dovile.DAO.UserDAO;
import com.dovile.model.Role;
import com.dovile.model.User;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nikanoras
 */
@Component
public class MyUserDetails implements UserDetailsService {

    private final static Log LOG = LogFactory.getLog(MyUserDetails.class);
    @Autowired
    private PasswordEncoder pe;
    
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = userDAO.findByUsername(username);
  
        // what does this do  ?
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        
        for (Role role : user.getRoleList()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
     
//return user;
        }
    }


