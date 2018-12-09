/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.model.requests;

import com.dovile.model.Basket;
import com.dovile.model.Role;
import java.util.List;

/**
 *
 * @author Dovile
 */
public class RegisterRequest {
    
    private String username;
    private String password;
    private String passwordConfirm;

    public RegisterRequest(String username, String password, String passwordConfirm, List<Role> roleList) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public RegisterRequest() {
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    
}
