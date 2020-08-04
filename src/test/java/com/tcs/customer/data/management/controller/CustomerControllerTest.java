package com.tcs.customer.data.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.customer.data.management.controller.PersonController;
import com.tcs.customer.data.management.entity.PersonEntity;
import com.tcs.customer.data.management.exception.NotFoundException;
import com.tcs.customer.data.management.service.PersonService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import utils.TestHelper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService service;

    @Before
    public void init() throws NotFoundException {

        List<PersonEntity> customers = new ArrayList<>();
        PersonEntity customer1 = TestHelper.createCustomer(1l, "address 1", 25, "Abhishek", "SA");
        PersonEntity customer2 = TestHelper.createCustomer(2l, "address 2", 26, "Mike", "Oliver");
        PersonEntity customer3 = TestHelper.createCustomer(3l, "address 3", 27, "Pratik", "SS");
        customers.add(customer1);
        customers.add(customer2);

        List<PersonEntity> searchedCustomers = new ArrayList<>();
        searchedCustomers.add(customer2);

        when(service.getCustomerById(3l)).thenReturn(customer3);
        when(service.getAllCustomers()).thenReturn(customers);
        when(service.searchByFirstAndOrLastName("Mike","Oliver")).thenReturn(searchedCustomers);
    }

    @Test
    public void testGetAllCustomers() throws Exception {

        MvcResult result = mvc.perform(get("/v1/customers"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Assert.assertThat(content, CoreMatchers.containsString("address 1"));
        Assert.assertThat(content, CoreMatchers.containsString("address 2"));
        Assert.assertThat(content, CoreMatchers.containsString("Abhishek"));
        Assert.assertThat(content, CoreMatchers.containsString("SA"));
        Assert.assertThat(content, CoreMatchers.containsString("Mike"));
        Assert.assertThat(content, CoreMatchers.containsString("Oliver"));
    }

    @Test
    public void testGetCustomerById() throws Exception {

        MvcResult result = mvc.perform(get("/v1/customers/{id}",  3L))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Assert.assertThat(content, CoreMatchers.containsString("address 3"));
        Assert.assertThat(content, CoreMatchers.containsString("Pratik"));
        Assert.assertThat(content, CoreMatchers.containsString("SS"));
        Assert.assertThat(content, CoreMatchers.containsString("27"));
        Assert.assertThat(content, CoreMatchers.containsString("3"));
    }

    @Test
    public void testGetCustomerByIdNotFound() throws Exception {

        MvcResult result = mvc.perform(get("/v1/customers/{id}",  1L))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);
        String content = result.getResponse().getContentAsString();
        assertEquals("",content);
    }

    @Test
    public void testSaveOrUpdate() throws Exception {

        PersonEntity customer = TestHelper.createCustomer(3l, "address 3", 27, "Pratik", "SS");
        when(service.saveOrUpdateCustomer(any(PersonEntity.class))).thenReturn(customer);

        /* Test fpr save */
        PersonEntity savedCustomer = service.saveOrUpdateCustomer(customer);

        MvcResult result = mvc.perform(post("/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Assert.assertThat(content, CoreMatchers.containsString("address 3"));
        Assert.assertThat(content, CoreMatchers.containsString("Pratik"));
        Assert.assertThat(content, CoreMatchers.containsString("SS"));
        Assert.assertThat(content, CoreMatchers.containsString("27"));
        Assert.assertThat(content, CoreMatchers.containsString("3"));

        /* Test for update */
        customer.setAddress("updated address 3");
        result = mvc.perform(post("/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andReturn();

        content = result.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Assert.assertThat(content, CoreMatchers.containsString("updated address 3"));
    }

    @Test
    public void testSearchByFirstAndOrLastName() throws Exception {
        MvcResult result = mvc.perform(get("/v1/customers/filter")
                .param("firstName","Mike").param("lastName","Oliver"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Assert.assertThat(content, CoreMatchers.containsString("address 2"));
        Assert.assertThat(content, CoreMatchers.containsString("Oliver"));
        Assert.assertThat(content, CoreMatchers.containsString("Mike"));
        Assert.assertThat(content, CoreMatchers.containsString("26"));
        Assert.assertThat(content, CoreMatchers.containsString("2"));
    }

    @Test
    public void testSearchByFirstAndOrLastNameNotFound() throws Exception {
        MvcResult result = mvc.perform(get("/v1/customers/filter")
                .param("firstName","Abhishek").param("lastName","SA"))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(result);
        String content = result.getResponse().getContentAsString();
        assertEquals("[]",content);
    }
}
