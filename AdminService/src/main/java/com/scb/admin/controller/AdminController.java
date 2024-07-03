package com.scb.admin.controller;


import com.scb.admin.dto.TransactionData;
import com.scb.admin.entity.AdminAccount;
import com.scb.admin.entity.Customer;
import com.scb.admin.exception.CustomerNotFoundException;
import com.scb.admin.projections.CustomerDTO;
import com.scb.admin.repository.CustomerViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CacheConfig(cacheNames = "customer")
@RestController
@RequestMapping("/admin")
@RestControllerAdvice
public class AdminController {

    @Autowired
    private CustomerViewRepository customerViewRepository;

    @PostMapping("/adminLogin")
    public ResponseEntity<Customer> adminLogin(@RequestBody TransactionData transactionData) {
        AdminAccount adminAccount = new AdminAccount();
        if (adminAccount.getUsername().equals(transactionData.getUserName()) && adminAccount.getPassword().equals(transactionData.getPassword())) {
            return new ResponseEntity<>(new Customer(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerViewRepository.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/customerByName/{name}")
    public ResponseEntity<CustomerDTO> getCustomerByName(@PathVariable String name) throws CustomerNotFoundException {
        CustomerDTO loginDetails = customerViewRepository.findByName(name).orElseThrow(()->new CustomerNotFoundException("Customer not found with name: "+name));
        return new ResponseEntity<>(loginDetails, HttpStatus.OK);
    }

    @Cacheable(key = "#id")
    @GetMapping("/customer/{id}")
    public Customer getCustomerById(@PathVariable long id) {
        return customerViewRepository.findById(id).get();
    }

    @CachePut(key = "#id")
    @PutMapping("/customer/{id}")
    public Customer updateCustomer(@PathVariable long id,@RequestBody Customer vehicle){
        return customerViewRepository.save(vehicle);
    }

    @CacheEvict(key = "#id")
    @DeleteMapping("/customer/{id}")
    public void deleteCustomer(@PathVariable long id){
        customerViewRepository.deleteById(id);
    }
}