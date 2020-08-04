package com.tcs.customer.data.management.dao;

import com.tcs.customer.data.management.entity.PersonEntity;
import com.tcs.customer.data.management.exception.NotFoundException;

import java.util.List;

public interface PersonDao {

    List<PersonEntity> getAllCustomers();

    PersonEntity getCustomerById(Long id) throws NotFoundException;

    PersonEntity saveOrUpdate(PersonEntity person);

    List<PersonEntity> searchByFirstAndOrLastName(String firstName, String lastName);
}