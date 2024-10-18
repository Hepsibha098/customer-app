package com.example.customer.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer.entity.Customer;
import com.example.customer.exception.CustomerExist;
import com.example.customer.exception.CustomerNameOrEmail;
import com.example.customer.exception.CustomerNotFound;
import com.example.customer.exception.DeletedCustomer;
import com.example.customer.exception.InvalidInput;
import com.example.customer.service.CustomerImple;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cus")
public class CusomerController {

    @Autowired
    private CustomerImple cImple;
    

  
    @PostMapping("/signup")
    public Customer saving(@Valid @RequestBody Customer customer) throws InvalidInput, IOException, CustomerExist{
        return cImple.saveCustomer(customer);
    }

    @GetMapping("/fetchall")
    public List<Customer> getAll(){
        return cImple.getAll();
    }

    @GetMapping("/fetchone/{id}")
    public Customer getCusById(@PathVariable Integer id) throws CustomerNotFound, InvalidInput{
        return cImple.getCustomer(id);
    }
    
    @GetMapping("/fetchnameoremail/{nameoremail}")
    public ResponseEntity<Customer> getCusByEmail( @PathVariable String nameoremail) throws CustomerNameOrEmail, InvalidInput{
        return cImple.getEmail(nameoremail);
    }
    @PutMapping("/put/{cusId}")
    public Customer putUpdate(@PathVariable Integer cusId , @RequestBody Customer customer ) throws CustomerNotFound{
        return cImple.putUpdate(cusId,customer);

    }
    @DeleteMapping("/delete/{cusId}")
    public String delete(@PathVariable Integer cusId) throws DeletedCustomer, InvalidInput{
         return cImple.delete(cusId);
    }

    @PatchMapping("/patchupdate/{cusId}")
    public Customer updatingPatch(@RequestBody Map<String,Object> customer ,@PathVariable Integer cusId) throws InvalidInput{
        return cImple.updatePatch(customer,cusId);
    }

    @GetMapping("/sendmail/{id}")
    public String sendingMail( @PathVariable Integer id) throws InvalidInput{
       return cImple.sendingMail(id);
    }

   // @PostMapping("/signup")
    // public Customer saving(@Valid @RequestParam("file") MultipartFile file ,@RequestParam("cusName") String cusName ,@RequestParam("age") Integer age, @RequestParam("email") String email) throws InvalidInput, IOException{
    //     return cImple.saveCustomer(file,cusName,age,email);
    // }
    @GetMapping("/connect")
    public String connect(){
        return "connection established";
    }


}
