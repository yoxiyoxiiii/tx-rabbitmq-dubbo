package com.example.txatomikos.domain.order;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "code", nullable = false)
	private Integer code;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

}
