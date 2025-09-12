package com.example.texnoerams.service.impl;

import com.example.texnoerams.dao.entity.CustomerEntity;
import com.example.texnoerams.dao.repository.CustomerRepository;
import com.example.texnoerams.dto.request.customer.CreateCustomerRequest;
import com.example.texnoerams.dto.request.customer.UpdateCustomerRequest;
import com.example.texnoerams.dto.response.CustomerResponse;
import com.example.texnoerams.exception.customer.CustomerAlreadyExistException;
import com.example.texnoerams.exception.customer.CustomerNotFoundException;
import com.example.texnoerams.exception.customer.FinLengthException;
import com.example.texnoerams.service.abstraction.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.texnoerams.enums.Status.*;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor()
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        if (customerRepository.existsByFin(request.getFin()))
            throw new CustomerAlreadyExistException("Customer with this FIN already exists!");

        CustomerEntity customer = CustomerEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fin(request.getFin())
                .pincode(request.getPincode())
                .status(ACTIVE)
                .build();

        if (customer.getFin().length() != 7)
            throw new FinLengthException("Fin should be 7 digits");

        customerRepository.save(customer);

        return buildCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<CustomerEntity> customers = customerRepository.findAll();
        List<CustomerResponse> customerResponses = new ArrayList<>();

        for (CustomerEntity customer : customers) {
            if (customer.getStatus().equals(ACTIVE) || customer.getStatus().equals(IN_PROGRESS))
                customerResponses.add(buildCustomerResponse(customer));
        }

        return customerResponses;
    }

    @Override
    public CustomerResponse getCustomerById(Long id) {
        CustomerEntity customer = fetchCustomerIfExist(id);

        return buildCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {
        CustomerEntity customer = fetchCustomerIfExist(id);

        if (request.getFirstName() != null && !request.getFirstName().trim().isEmpty()) {
            customer.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            customer.setLastName(request.getLastName());
        }

        customer.setStatus(IN_PROGRESS);

        customerRepository.save(customer);

        return buildCustomerResponse(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        CustomerEntity customer = fetchCustomerIfExist(id);

        customer.setStatus(DELETED);
        customerRepository.save(customer);
    }

    private CustomerEntity fetchCustomerIfExist(Long id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new CustomerNotFoundException("Customer not found: " + id));
    }

    private CustomerResponse buildCustomerResponse(CustomerEntity customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .fin(customer.getFin())
                .pincode(customer.getPincode())
                .status(customer.getStatus())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}
