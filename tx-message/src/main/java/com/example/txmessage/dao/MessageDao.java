package com.example.txmessage.dao;

import com.example.txmessage.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "messageDao")
public interface MessageDao extends JpaRepository<Message, String> {
}
