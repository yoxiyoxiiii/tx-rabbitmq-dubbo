package com.example.txatomikos.domain.customer;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "customer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable{

	/**
	 * 目前测试的时候 ，主键ID 必须自增，否则事务无法回滚（原因不明）
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "age", nullable = false)
	private Integer age;

}
