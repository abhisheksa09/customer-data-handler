API Documentation: 
http://localhost:9090/swagger-ui.html

To view the data in the H2 database: 
http://localhost:9090/h2-console

CRUD APIs: 

1> Add a new customer: 

Use tool like PostMan to POST this data and create a new customer: 

http://localhost:9090/v1/customers : POST

{
  "address": "Address Detail",
  "age": 25,
  "id": 1,
  "firstName": "John",
  "lastName": "Doe"
}

2> Retrieve all customers: 

http://localhost:9090/v1/customers


3> Retrieve one customer by it's identifier: 

Sample URL: http://localhost:9090/v1/customers/2

4> Update the living address: 

Use tool like PostMan to POST this data and update a customer's address: 

http://localhost:9090/v1/customers : POST

{
  "address": "Address Detail- updated",
  "age": 25,
  "id": 1,
  "firstName": "John",
  "lastName": "Doe"
}

5> Search customers by first name and/or last name: 

Sample URLs: 
http://localhost:9090/v1/customers/filter?firstName=john&lastName=doe or 
http://localhost:9090/v1/customers/filter?lastName=doe or 
http://localhost:9090/v1/customers/filter?firstName=john






