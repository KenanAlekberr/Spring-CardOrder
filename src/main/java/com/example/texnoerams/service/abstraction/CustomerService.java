package com.example.texnoerams.service.abstraction;

import com.example.texnoerams.dao.entity.CustomerEntity;
import com.example.texnoerams.dto.request.customer.CreateCustomerRequest;
import com.example.texnoerams.dto.request.customer.UpdateCustomerRequest;
import com.example.texnoerams.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CreateCustomerRequest request);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(Long id);

    CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request);

    void deleteCustomer(Long id);

    CustomerEntity getCustomerByFin(String fin);
}