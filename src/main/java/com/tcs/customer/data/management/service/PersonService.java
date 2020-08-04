package com.tcs.customer.data.management.service;

import com.tcs.customer.data.management.entity.PersonEntity;
import com.tcs.customer.data.management.exception.BadRequestException;
import com.tcs.customer.data.management.exception.NotFoundException;

import java.util.List;

public interface PersonService {

    List<PersonEntity> getAllCustomers();

    PersonEntity getCustomerById(Long id) throws NotFoundException;

    PersonEntity saveOrUpdateCustomer(PersonEntity person) throws BadRequestException;

    List<PersonEntity> searchByFirstAndOrLastName(String firstName, String lastName);
}
