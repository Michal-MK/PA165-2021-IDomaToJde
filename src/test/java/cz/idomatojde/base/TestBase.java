package cz.idomatojde.base;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

//@ContextConfiguration(classes = PersistenceAppContext.class)
public class TestBase extends AbstractTestNGSpringContextTests {

    @PersistenceContext
    protected EntityManager em;

    @PersistenceUnit
    protected EntityManagerFactory emf;
}
