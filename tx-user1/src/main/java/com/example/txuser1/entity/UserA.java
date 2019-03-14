package com.example.txuser1.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tx_user_a")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserA implements Serializable {

    @Id
    private String id;

    private String username;

    private String password;
}
