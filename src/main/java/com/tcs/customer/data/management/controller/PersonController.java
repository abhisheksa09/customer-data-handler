package com.tcs.customer.data.management.controller;

import com.tcs.customer.data.management.entity.PersonEntity;
import com.tcs.customer.data.management.exception.BadRequestException;
import com.tcs.customer.data.management.exception.NotFoundException;
import com.tcs.customer.data.management.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;
 
@RestController
@RequestMapping("/")
public class PersonController
{
    @Autowired
    PersonService service;

    @GetMapping("/v1/customers")
    public ResponseEntity<List<PersonEntity>> getAllCustomers() {

        List<PersonEntity> customers = service.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
 
    @GetMapping("/v1/customers/{id}")
    public ResponseEntity<PersonEntity> getCustomerById(@PathVariable("id") Long id)
                                                    throws NotFoundException {

        PersonEntity customer = service.getCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/v1/customers/filter")
    public ResponseEntity<List<PersonEntity>> searchByFirstAndOrLastName(@QueryParam("firstName") String firstName,
                                                                         @QueryParam("lastName") String lastName) {

        List<PersonEntity> customers = service.searchByFirstAndOrLastName(firstName, lastName);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
 
    @PostMapping("/v1/customers")
    public ResponseEntity<PersonEntity> createOrUpdateCustomer(@RequestBody PersonEntity person) throws BadRequestException {

        PersonEntity updatedCustomer = service.saveOrUpdateCustomer(person);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }
}