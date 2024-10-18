package com.example.customer.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.customer.CustRepo.CustomerRepo;
import com.example.customer.entity.Customer;
import com.example.customer.exception.CustomerExist;
import com.example.customer.exception.CustomerNameOrEmail;
import com.example.customer.exception.CustomerNotFound;
import com.example.customer.exception.DeletedCustomer;
import com.example.customer.exception.InvalidInput;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerImple {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private   EmailService emailService;

  
    // customer signup
    public Customer saveCustomer(Customer customer) throws IOException, InvalidInput, CustomerExist {

        Customer exist = customerRepo.getCusByEmail(customer.getEmail());
        if (exist != null) {
            throw new CustomerExist(" Already customer is Exist ,kindly Try With Another Mail id");
        }
        String customerName = customer.getCusName();
        String customerMail = customer.getEmail();

        if (customerName.charAt(0) != ' ') {
            log.info("No space for name ");
            String mailExten = customerMail.substring(customerMail.length() - 9, customerMail.length());
            if (mailExten.equals("gmail.com")) {
                log.info("well extension for mail id");
                return customerRepo.save(customer);
            } else
                throw new InvalidInput("Mail extension not gave properly kindly provide vaklid one ");
        } else
            throw new InvalidInput("Name started with space kindly remove space before name");
    }

    // to listout all the customer details
    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    // to get the particular customer details by using customer id
    public Customer getCustomer(Integer cusId) throws CustomerNotFound, InvalidInput {
        if (cusId != null && cusId != 0) {
            Customer cus = customerRepo.findByCusId(cusId);
            if (cus != null) {
                return cus;
            } else {
                throw new CustomerNotFound(" customer not found with customer id " + cusId);
            }
        } else {
            throw new InvalidInput(" Given Id " + cusId + " is Invalid Provide a VALID Id  ");
        }
    }

    // getting the customer details by using customer Name or mail id
    public ResponseEntity<Customer> getEmail(String nameoremail) throws CustomerNameOrEmail, InvalidInput {
        if (!nameoremail.isBlank()) {
            Customer cus = customerRepo.getCusByEmail(nameoremail);
            if (cus != null) {

                return new ResponseEntity<>(cus, HttpStatus.FOUND);
            } else {
                // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                throw new CustomerNameOrEmail(" There is No USER with this Name or Email  " + nameoremail);
            }
        } else {
            throw new InvalidInput("Provide Either Name or Email  " + nameoremail);
        }
    }

    // Updating the customer details by using customer id
    public Customer putUpdate(Integer cusId, Customer customer) throws CustomerNotFound {
        log.info("customer===="+customer);
        Customer cus = customerRepo.findByCusId(cusId);
        log.info("cus===="+cus);
        if (cus != null) {
            cus.setCusName(customer.getCusName());
            cus.setEmail(customer.getEmail());
            cus.setAge(customer.getAge());
            cus.setCusId(customer.getCusId());
            if(customer.getProduct()!=null){
                cus.setProduct(customer.getProduct());
            }
           
            return customerRepo.save(cus);
        } else {
            throw new CustomerNotFound(" Customer is NotFound with id " + cusId);
        }
    }

    public String delete(Integer cusId) throws DeletedCustomer, InvalidInput {
        Customer cus = customerRepo.findByCusId(cusId);
        if (cus != null) {
            customerRepo.deleteById(cusId);
            return "successfully deleted ";
            // throw new DeletedCustomer(" Customer Deleted of id " + cusId);
        } else {
            throw new InvalidInput(" Invalid Customer Kindly provide valid Id");
        }
    }

    // updating the customer details by using customer id through patchMapping
    public Customer updatePatch(Map<String, Object> customerMap, Integer cusId) throws InvalidInput {
        Customer cus = customerRepo.findByCusId(cusId);
        if (cus != null) {
            customerMap.forEach((key, value) -> {
                Field field = ReflectionUtils.findRequiredField(Customer.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, cus, value);

            });
            return customerRepo.save(cus);

        } else {
            throw new InvalidInput("Customer Id NoT There");
        }

    }

    // sending mail to who have that customer id
    public String sendingMail(Integer cusId) throws InvalidInput {
        Customer cus = customerRepo.findByCusId(cusId);
        if (cus != null) {
            StringBuilder builder = new StringBuilder();
            builder.append("Customer Id : " + cus.getCusId() + "\n customer Email id : " + cus.getEmail()
                    + "\n Customer Name :" + cus.getCusName() + "\n Customer Age " + cus.getAge());
            emailService.sendMail(cus.getEmail(), "Product Details ", builder.toString());
            return "Success";

        } else {
            throw new InvalidInput(" No Customer with this id");
        }

    }

}
