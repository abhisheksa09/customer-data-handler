package com.tcs.customer.data.management.service;

import com.tcs.customer.data.management.dao.PersonDao;
import com.tcs.customer.data.management.entity.PersonEntity;
import com.tcs.customer.data.management.exception.BadRequestException;
import com.tcs.customer.data.management.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
 
@Service
public class PersonServiceImpl implements PersonService{

    @Autowired
    private PersonDao personDao;

    @Override
    public List<PersonEntity> getAllCustomers() {

        return personDao.getAllCustomers();
    }

    @Override
    public PersonEntity getCustomerById(Long id) throws NotFoundException {

        return personDao.getCustomerById(id);
    }

    @Override
    public PersonEntity saveOrUpdateCustomer(PersonEntity person) throws BadRequestException {

        if(person.getId() == null || person.getId() <= 0) throw new BadRequestException("Bad Request: ID cannot be empty");

        return personDao.saveOrUpdate(person);
    }

    @Override
    public List<PersonEntity> searchByFirstAndOrLastName(String firstName, String lastName) {

        return personDao.searchByFirstAndOrLastName(firstName,lastName);
    }
}