package utils;

import com.tcs.customer.data.management.entity.PersonEntity;

public class TestHelper {

    public static PersonEntity createCustomer(long id, String address, int age, String firstName, String lastName) {
        PersonEntity customer = new PersonEntity();
        customer.setId(id);
        customer.setAddress(address);
        customer.setAge(age);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        return customer;
    }
}
