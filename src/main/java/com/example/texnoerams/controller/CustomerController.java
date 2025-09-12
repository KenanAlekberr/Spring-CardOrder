package com.example.texnoerams.controller;

import com.example.texnoerams.dto.request.customer.CreateCustomerRequest;
import com.example.texnoerams.dto.request.customer.UpdateCustomerRequest;
import com.example.texnoerams.dto.response.CustomerResponse;
import com.example.texnoerams.service.abstraction.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@FieldDefaults(level = PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor()
public class CustomerController {
    CustomerService customerService;

    @PostMapping("/post")
    public CustomerResponse createCustomer(@RequestBody CreateCustomerRequest request) {
        return customerService.createCustomer(request);
    }

    @GetMapping("/getAll")
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/get/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/put/{id}")
    public CustomerResponse updateCustomer(@PathVariable Long id, @RequestBody UpdateCustomerRequest request) {
        return customerService.updateCustomer(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}