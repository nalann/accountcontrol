package com.account.control.service;

import com.account.control.common.CustomerConverter;
import com.account.control.exception.CustomerNotFoundException;
import com.account.control.model.dto.CustomerDto;
import com.account.control.model.entity.Customer;
import com.account.control.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    private final CustomerConverter customerConverter = new CustomerConverter();

    public Customer findCustomerById(Integer id){
        return customerRepository.findById(id).orElseThrow(() ->
                new CustomerNotFoundException("Customer does not found: " + id));
    }

    public CustomerDto findCustomer(Integer id){
        return customerConverter.convertCustomerToCustomerDto(
                customerRepository.findById(id).orElseThrow(() ->
                        new CustomerNotFoundException("Custoemr does not found: " + id)));
    }

    public List<CustomerDto> findAllCustomers(){
        List<CustomerDto> customerDtos = new ArrayList<>();
        customerRepository.findAll().stream().forEach(customer -> {
            customerDtos.add(customerConverter.convertCustomerToCustomerDto(customer));
        });
        return customerDtos;
    }

    public void updateCustomer(Customer customer){
        customerRepository.save(customer);
    }
}
