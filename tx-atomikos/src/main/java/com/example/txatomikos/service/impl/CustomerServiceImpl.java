package com.example.txatomikos.service.impl;

import com.example.txatomikos.dao.customer.CustomerRepository;
import com.example.txatomikos.dao.order.OrderRepository;
import com.example.txatomikos.domain.customer.Customer;
import com.example.txatomikos.domain.order.Order;
import com.example.txatomikos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveCustomerAndOrder(Customer customer, Order order) throws InterruptedException {
        Thread.sleep(30000);
        customerRepository.save(customer);
        orderRepository.save(order);
        return true;
    }

    @Override
    public String findById(int i) {
        return customerRepository.findById(i).get().getName();
    }
}
