package com.tcs.customer.data.dao;

import com.tcs.customer.data.management.dao.PersonDao;
import com.tcs.customer.data.management.dao.PersonDaoImpl;
import com.tcs.customer.data.management.entity.PersonEntity;
import com.tcs.customer.data.management.exception.NotFoundException;
import com.tcs.customer.data.management.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import utils.TestHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PersonDaoImplTest {

    @TestConfiguration
    static class PersonDaoImplConfiguration {
        @Bean
        public PersonDao personDao() {
            return new PersonDaoImpl();
        }
    }

    @Autowired
    private PersonDao dao;

    @MockBean
    private PersonRepository repository;

    @Before
    public void init() {
        List<PersonEntity> customers = new ArrayList<>();
        PersonEntity customer1 = TestHelper.createCustomer(1l, "address 1", 25, "John", "Doe");
        PersonEntity customer2 = TestHelper.createCustomer(2l, "address 2", 26, "Mike", "Oliver");
        PersonEntity customer3 = TestHelper.createCustomer(3l, "address 3", 27, "Johnny", "Doe");
        Optional<PersonEntity> customer3Optional = Optional.of(customer3);
        customers.add(customer1);
        customers.add(customer2);

        List<PersonEntity> searchedCustomers = new ArrayList<>();
        searchedCustomers.add(customer2);

        when(repository.save(customer3)).thenReturn(customer3);
        when(repository.findById(3l)).thenReturn(customer3Optional);
        when(repository.findAll()).thenReturn(customers);
        when(repository.searchByFirstAndOrLastName("Mike","Oliver")).thenReturn(searchedCustomers);
    }

    @Test
    public void testGetAllCustomers() {
        List<PersonEntity> customers = dao.getAllCustomers();
        assertNotNull(customers);
        assertEquals(2, customers.size());
        PersonEntity customer = customers.get(0);
        assertNotNull(customer);
        assertEquals(1l, (long) customer.getId());
        assertEquals(25, customer.getAge());
        assertEquals("address 1", customer.getAddress());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
    }

    @Test
    public void testGetCustomerById() throws NotFoundException {
        PersonEntity customer = dao.getCustomerById(3l);
        assertNotNull(customer);

        assertEquals(3l, (long) customer.getId());
        assertEquals(27, customer.getAge());
        assertEquals("address 3", customer.getAddress());
        assertEquals("Johnny", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
    }

    @Test(expected = NotFoundException.class)
    public void testGetEmptyCustomerList() throws NotFoundException {
        PersonEntity customer = dao.getCustomerById(2L);
        assertNull(customer);
    }

    @Test
    public void testSaveOrUpdate() throws Exception {

        /* Test fpr save */
        PersonEntity customer3 = TestHelper.createCustomer(3l, "address 3", 27, "Johnny", "Doe");
        PersonEntity savedCustomer = dao.saveOrUpdate(customer3);

        assertEquals(3L, (long) savedCustomer.getId());
        assertEquals(27,  savedCustomer.getAge());
        assertEquals("address 3",  savedCustomer.getAddress());
        assertEquals("Johnny",  savedCustomer.getFirstName());
        assertEquals("Doe",  savedCustomer.getLastName());

        /* Test for update */
        customer3.setAddress("updated address 3");
        PersonEntity updatedCustomer = dao.saveOrUpdate(customer3);

        assertEquals(3L, (long) savedCustomer.getId());
        assertEquals(27,  savedCustomer.getAge());
        assertEquals("updated address 3",  savedCustomer.getAddress());
        assertEquals("Johnny",  savedCustomer.getFirstName());
        assertEquals("Doe",  savedCustomer.getLastName());
    }

    @Test
    public void testSearchByFirstAndOrLastName() throws NotFoundException {
        List<PersonEntity> customers = dao.searchByFirstAndOrLastName("Mike", "Oliver");
        assertNotNull(customers);
        assertEquals(1, customers.size());
        PersonEntity customer = customers.get(0);
        assertNotNull(customer);
        assertEquals(2l, (long) customer.getId());
        assertEquals(26, customer.getAge());
        assertEquals("address 2", customer.getAddress());
        assertEquals("Mike", customer.getFirstName());
        assertEquals("Oliver", customer.getLastName());
    }

    @Test
    public void testSearchByFirstAndOrLastNameNotFound() throws NotFoundException {
        List<PersonEntity> customers = dao.searchByFirstAndOrLastName("John", "Oliver");
        assertNotNull(customers);
        assertEquals(0, customers.size());
    }
}