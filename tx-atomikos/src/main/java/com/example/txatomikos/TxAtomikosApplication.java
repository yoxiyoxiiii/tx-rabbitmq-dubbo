package com.example.txatomikos;

import com.example.txatomikos.domain.customer.Customer;
import com.example.txatomikos.domain.order.Order;
import com.example.txatomikos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Atomikos 适合 强一致性事务，并且是单进程下同时操作多个数据源的情况
 * 性能问题
 * */
@SpringBootApplication(scanBasePackages = "com.example.txatomikos")
@RestController
public class TxAtomikosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TxAtomikosApplication.class, args);
	}


	@Autowired
	private CustomerService customerService;

	@GetMapping
	public boolean save() throws InterruptedException {
		Customer customer = Customer.builder().age(10).name("xxxx").build();
		Order build = Order.builder().code(4).quantity(5).build();
		boolean customerAndOrder = customerService.saveCustomerAndOrder(customer, build);
		return customerAndOrder;
	}

	@GetMapping("get")
	public String get() {
		String name = customerService.findById(1);
		return name;
	}



}
