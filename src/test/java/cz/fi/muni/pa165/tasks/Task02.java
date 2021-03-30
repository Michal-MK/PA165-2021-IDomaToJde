package cz.fi.muni.pa165.tasks;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import cz.fi.muni.pa165.entity.Product;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

 
@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task02 extends AbstractTestNGSpringContextTests {

	Category electro;
	Category kitchen;


	Product flashlight;
	Product robot;
	Product plate;

	@PersistenceUnit
	private EntityManagerFactory emf;

	@BeforeClass
	public void beforeClass() {
		electro = new Category();
		electro.setName("Electro");

		kitchen = new Category();
		kitchen.setName("Kitchen");

		flashlight = new Product();
		flashlight.setName("Flashlight");
		robot = new Product();
		robot.setName("Kitchen robot");
		plate = new Product();
		plate.setName("Plate");

		flashlight.addCategory(electro);
		robot.addCategory(electro);
		robot.addCategory(kitchen);
		plate.addCategory(kitchen);

			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			em.persist(electro);
			em.persist(kitchen);
			em.persist(flashlight);
			em.persist(robot);
			em.persist(plate);

			em.getTransaction().commit();

	}

	@Test
	void test1() {
		var manager = emf.createEntityManager();
		Set<Category> cats = new HashSet<>(manager.createQuery("select c from Category c",Category.class)
				.getResultList());

		assertContainsCategoryWithName(cats, "Electro");
	}

	@Test
	void test2() {
		var manager = emf.createEntityManager();
		Set<Category> cats = new HashSet<>(manager.createQuery("select c from Category c",Category.class)
				.getResultList());

		assertContainsCategoryWithName(cats, "Kitchen");
	}

	@Test
	void test3() {
		var manager = emf.createEntityManager();
		Set<Product> prods = new HashSet<>(manager.createQuery("select p from Product p", Product.class)
				.getResultList());

		assertContainsProductWithName(prods, "Flashlight");
	}

	@Test
	void test4() {
		var manager = emf.createEntityManager();
		Set<Product> prods = new HashSet<>(manager.createQuery("select p from Product p", Product.class)
				.getResultList());

		assertContainsProductWithName(prods, "Kitchen robot");
	}

	@Test
	void test5() {
		var manager = emf.createEntityManager();
		Set<Product> prods = new HashSet<>(manager.createQuery("select p from Product p", Product.class)
				.getResultList());

		assertContainsProductWithName(prods, "Plate");
	}

	@Test(expectedExceptions = ConstraintViolationException.class)
	void test6() {
		var manager = emf.createEntityManager();

		var p = new Product();
		p.setName(null);

		manager.getTransaction().begin();

		manager.persist(p);

		manager.getTransaction().commit();
	}


	@Test(expectedExceptions = ConstraintViolationException.class)
	void test7() {
		var manager = emf.createEntityManager();

		var p = new Product();
		p.setImage(new byte[0]);

		manager.getTransaction().begin();

		manager.persist(p);

		manager.getTransaction().commit();
	}




	private void assertContainsCategoryWithName(Set<Category> categories, String expectedCategoryName) {
		for(Category cat: categories){
			if (cat.getName().equals(expectedCategoryName))
				return;
		}
			
		Assert.fail("Couldn't find category " + expectedCategoryName + " in collection " + categories);
	}
	private void assertContainsProductWithName(Set<Product> products, String expectedProductName) {
		for(Product prod: products){
			if (prod.getName().equals(expectedProductName))
				return;
		}
			
		Assert.fail("Couldn't find product " + expectedProductName + " in collection " + products);
	}
}
