package com.tcs.customer.data.management.dao;

import com.tcs.customer.data.management.entity.PersonEntity;
import com.tcs.customer.data.management.exception.NotFoundException;
import com.tcs.customer.data.management.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDaoImpl implements PersonDao {

    @Autowired
    PersonRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);

    @Override
    public List<PersonEntity> getAllCustomers() {

        List<PersonEntity> customers = repository.findAll();

        if(customers.size() > 0) {
            return customers;
        } else {
            return new ArrayList<PersonEntity>();
        }
    }

	@Override
    public PersonEntity getCustomerById(Long id) throws NotFoundException {

        logger.info("Getting customer details for ID: "+ id);
        Optional<PersonEntity> customer = repository.findById(id);

        if(customer.isPresent()) {
            return customer.get();
        } else {
            throw new NotFoundException("Not Found: No customer record exist for given id");
        }
    }

    @Override
    public PersonEntity saveOrUpdate(PersonEntity person) {

        Optional<PersonEntity> customer = repository.findById(person.getId());

        if(customer.isPresent())
        {
            PersonEntity personEntity = customer.get();
            personEntity.setAge(person.getAge());
            personEntity.setFirstName(person.getFirstName());
            personEntity.setLastName(person.getLastName());
            personEntity.setAddress(person.getAddress());

            personEntity = repository.save(personEntity);

            return personEntity;
        } else {
            person = repository.save(person);

            return person;
        }
    }

    @Override
    public List<PersonEntity> searchByFirstAndOrLastName(String firstName, String lastName) {

        List<PersonEntity> customers= repository.searchByFirstAndOrLastName(firstName,lastName);

        if(customers.size() > 0) {
            return customers;
        } else {
            return new ArrayList<>();
        }
    }
}