package com.example.Wallcart.dao;

import com.example.Wallcart.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByEmailId(String emailId);

    @Query(value = "SELECT * FROM customer WHERE gender = 'FEMALE'", nativeQuery = true)
    List<Customer> findFemaleCustomerByAge();

    @Query(value = "SELECT * FROM customer", nativeQuery = true)
    List<Customer> customersWithKOrders();
}
