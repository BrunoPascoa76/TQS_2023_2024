# Written Answers

## Lab 3.1

### a)

A couple of examples of AssertJ method chaining are:

* `A_EmployeeRepositoryTest.givenSetOfEmployees_whenFindAll_thenReturnAllEmployees();`
  * `assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());`
* `B_EmployeeService_UnitTest.given3Employees_whengetAll_thenReturn3Records();`
  * `assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());` 
* ...

### b)

An example where the repository is mocked is on B_EmployeeService_UnitTest.

### c)

The difference is that, while both create Mock objects, @MockBean adds it to the Spring Application Context, allowing for more seamless integration with the Spring framework.

### d)

The role of this file is that it overwrites the "application.properties" configurations during integration tests, allowing us to have separate environment for testing and for releases.

### e)

* C focuses on testing the MVC, mocking the remaining components
* D focuses on testing the data layer, mocking the restTemplate
* E is a full-stack tests, where nothing is mocked
  
While C and D focus on testing individual layers in isolation, mocking the remaining components to make it less resource-intensive (and, therefore, faster), E are full-stack tests that focus on testing the "pipeline" (checking if the layers interact with the others properly)