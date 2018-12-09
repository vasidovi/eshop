/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dovile.DAO;

import com.dovile.model.Role;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Nikanoras
 */
@Transactional

public interface RoleDAO extends JpaRepository<Role, Integer> {
   @Query("Select r from Role r where r.role=:role")
    public Role findByRole(@Param("role") String role);          
}
