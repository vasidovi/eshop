/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.DAO;

import com.dovile.model.Basket;
import com.dovile.model.BasketLine;
import com.dovile.model.Product;
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
public interface ProductDAO extends JpaRepository<Product, Integer> {
        @Query("Select p from Product p where p.name=:name")
    public Product findByName(@Param("name") String name);
    
}
