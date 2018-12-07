/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.DAO;

import com.dovile.model.Basket;
import com.dovile.model.User;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nikanoras
 */
@Transactional
public interface BasketDAO extends JpaRepository<Basket, Integer> {
  
    // new logic session no longer unique identificator, user can have several baskets during the same session 
//    @Query("Select b from Basket b where b.sessionId=:sessionId")
//    public Basket findBySessionId(@Param("sessionId") String sessionId); 
    
     @Query("Select b from Basket b where b.userId=:userId")
     public List<Basket> findByUser(@Param("userId") User userId); 
            
  }
    

