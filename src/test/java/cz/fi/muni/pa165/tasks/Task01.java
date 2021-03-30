package cz.fi.muni.pa165.tasks;

import cz.fi.muni.pa165.PersistenceSampleApplicationContext;
import cz.fi.muni.pa165.entity.Category;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@ContextConfiguration(classes = PersistenceSampleApplicationContext.class)
public class Task01 extends AbstractTestNGSpringContextTests {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@Test
	public void categoryTest() {
		Category cat;
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			cat = new Category();
			cat.setName("Test");
			em.persist(cat);
			em.getTransaction().commit();
		} finally {
			if (em != null) em.close();
		}


		//TODO under this line: create a second entity manager in categoryTest and use the find() method to find
		// the category and assert its name. Note that Category uses GenerationType.IDENTITY.

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			var found = em.find(Category.class,cat.getId());
			Assert.assertEquals(found, cat);
			em.getTransaction().commit();
		} finally {
			if (em != null) em.close();
		}
	}
}
