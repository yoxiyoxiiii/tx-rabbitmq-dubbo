package com.example.txatomikos.service;

import com.example.txatomikos.domain.customer.Customer;
import com.example.txatomikos.domain.order.Order;

public interface CustomerService {

    Integer saveCustomerAndOrder(Customer customer , Order order) throws InterruptedException;

    String findById(int i);
}
