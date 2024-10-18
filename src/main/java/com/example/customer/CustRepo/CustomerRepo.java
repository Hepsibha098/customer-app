package com.example.customer.CustRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.customer.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    
   
    @Query(value = "SELECT * FROM myschema.customer c WHERE c.cus_id =:cusId", nativeQuery = true) 
    public Customer findByCusId(@Param("cusId") Integer cusId);

    
    @Query(value = "SELECT * FROM myschema.customer c WHERE c.email =:emailorname or c.cus_name= :emailorname", nativeQuery = true)
    public Customer getCusByEmail(@Param("emailorname") String email);
}

    

   



