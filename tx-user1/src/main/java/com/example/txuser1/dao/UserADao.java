package com.example.txuser1.dao;

import com.example.txuser1.entity.UserA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface UserADao extends JpaRepository<UserA, String>{
}
