package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @EntityGraph(value = "Customer.detail", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select c from Customer c")
    List<Customer> retrieveAllCustomers();
}
