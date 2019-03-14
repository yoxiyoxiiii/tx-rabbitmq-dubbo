package com.example.txuser2.dao;

import com.example.txuser2.entity.UserB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface UserBDao extends JpaRepository<UserB, String>{
}
