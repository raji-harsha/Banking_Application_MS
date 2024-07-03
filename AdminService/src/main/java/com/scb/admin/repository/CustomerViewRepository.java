package com.scb.admin.repository;

import com.scb.admin.entity.Customer;
import com.scb.admin.projections.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerViewRepository extends JpaRepository<Customer, Long> {

    Optional<CustomerDTO> findByName(String name);
}
