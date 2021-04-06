package cz.idomatojde.base;

import cz.idomatojde.PersistenceAppContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;

@ContextConfiguration(classes = PersistenceAppContext.class)
public class TestBase extends AbstractTestNGSpringContextTests {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    protected EntityManager em;

    @PersistenceUnit
    protected EntityManagerFactory emf;
}
