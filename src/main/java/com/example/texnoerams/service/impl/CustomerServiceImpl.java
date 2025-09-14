package com.example.texnoerams.service.impl;

import com.example.texnoerams.dao.entity.CustomerEntity;
import com.example.texnoerams.dao.repository.CustomerRepository;
import com.example.texnoerams.dto.request.customer.CreateCustomerRequest;
import com.example.texnoerams.dto.request.customer.UpdateCustomerRequest;
import com.example.texnoerams.dto.response.CustomerResponse;
import com.example.texnoerams.exception.AlreadyExistException;
import com.example.texnoerams.exception.FinLengthException;
import com.example.texnoerams.exception.NotFoundException;
import com.example.texnoerams.service.abstraction.CustomerService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.texnoerams.enums.Status.ACTIVE;
import static com.example.texnoerams.enums.Status.DELETED;
import static com.example.texnoerams.enums.Status.IN_PROGRESS;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        CustomerEntity customer = buildCustomerEntity(request);

        if (request.getFin().length() != 7)
            throw new FinLengthException("Fin should be 7 digits");

        if (customerRepository.existsByFin(request.getFin()))
            throw new AlreadyExistException("Customer with this FIN already exists!");

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

        if (StringUtils.isNotEmpty(request.getFirstName())) {
            customer.setFirstName(request.getFirstName());
        }

        if (StringUtils.isNotEmpty(request.getLastName())) {
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

    @Override
    public CustomerEntity getCustomerByFin(String fin) {
        List<CustomerEntity> customers = customerRepository.findAll();

        for (CustomerEntity customer : customers) {
            if (customer.getFin().equals(fin))
                return customer;
        }

        return null;
    }

    private CustomerEntity fetchCustomerIfExist(Long id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Customer not found: " + id));
    }

    private CustomerResponse buildCustomerResponse(CustomerEntity customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .fin(customer.getFin())
                .status(customer.getStatus())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    private CustomerEntity buildCustomerEntity(CreateCustomerRequest request) {
        return CustomerEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .fin(request.getFin())
                .status(ACTIVE)
                .build();
    }
}