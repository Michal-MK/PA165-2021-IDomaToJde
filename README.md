# Persistence Seminar 02

In this seminar you will continue working with the JPA and Hibernate. Your tasks will be related to relationships, DAOs, and queries.

If you experience out of memory errors, you can use the following command in terminal to kill all java processes:
```
killall java
```

**Task 01**  
Modify class `Task01` (package `cz.fi.muni.pa165.tasks`) in directory `src/test/java/cz/fi/muni/pa165/tasks` to be a unit test that creates application context.  
Use the `@ContextConfiguration` annotation (see documentation [1], [2]) and configure it so that `PersistenceSampleApplicationContext.class`
is used as the spring configuration class.  
Your test class should also extend `AbstractTestNGSpringContextTests`.  
Test code is already in the file, you can run it and see if it doesn't fail.

Now you have running unit test, but it doesn't assert anything. Add assert to this test.  
Create a second entity manager in the methos `categoryTest` and use the `find()` method to find the category and assert its name.

**Task 02**
Create a bidirectional relationship many to many between `Product` and `Category`.  
A product can be placed in many categories and any category can have many products.  
Create a `java.util.Set` in each entity and use `@ManyToMany` annotation.  
Set `Category` as owning side. Owning side was covered on the lecture, if you are unsure what it is you can find it in JPA specification or 
in some tutorials on the internet.  
Don't forget to clean up all TODO items in `Product` and `Category` entities.  
There are several TODO items that require you to either delete some code or uncomment some code.

Then you need to write tests into file `Task02`. In the `@BeforeClass` first create two categories Electro and Kitchen. 
Then, still in`@BeforeClass` create 3 products: Flashlight, Kitchen robot, Plate.  
Place them in the appropriate categories and commit the transaction. Store these entity instances that you have created 
in `@BeforeClass` in instance variables, so that rest of the test methods can access them.

Then write 5 unit tests - one for each category and one for each product.

In each test create a new entity manager, search for the entity and assert that it has correct content of e.g. `java.util.Set`.

Hint: there are two helper methods prepared for you: `assertContainsCategoryWithName`, `assertContainsProductWithName`.

**Task 03**
Create DAO object for Product into package `cz.fi.muni.pa165.dao`. Interface `ProductDao` is already prepared for you, you need to implement 
only the implementation `ProductDaoImpl`. Use the `@Repository` annotation on the `ProductDaoImpl`. 
Implement `findAll`, `findById`, `findByName`, `create`, and `remove` methods. To get the entity manager inside DAO use `@PersistenceContext`.

To implement `findAll()`, you will need to use JPQL this is what you need:
```java
   em.createQuery("select p from Product p", Product.class).getResultList();
```

Then uncomment the code in `seminarservices.SeminarServiceImpl` class (this one uses your DAO) and add `@Transactional` annotation to the Service.

Now, just uncomment `Task03` unit test.

**Task 04**
Write JPQL queries and criteria API queries for tests in `Test04`. The Test file contains comments that ask you to write queries.

**Task 05**
Use beans validation to validate `Product`. Use `javax.validation.constraints.NotNull` annotation to make sure that the name cannot be set to null. 
Then write 1 new unit test in the `Task02` test class to verify your validations work. 
This test should expect exception, so the signature will be something like this:
```java
	@Test(expectedExceptions=ConstraintViolationException.class)
	public void testDoesntSaveNullName() {
``` 

Additional task: Write 1 additional test in `Task02` that will verify the constraint in `Product` defined by the `AllOrNothing` annotation.

[1] http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/  
[2] http://docs.spring.io/spring/docs/current/javadoc-api/
