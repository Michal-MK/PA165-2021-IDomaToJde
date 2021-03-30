package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDao {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void create(Product p) {
        manager.persist(p);
    }

    @Override
    public List<Product> findAll() {
        return manager.createQuery("select p from Product p", Product.class).getResultList();
    }

    @Override
    public Product findById(Long id) {
        return manager.find(Product.class,id);
    }

    @Override
    public void remove(Product p) {
        manager.remove(p);
    }

    @Override
    public List<Product> findByName(String name) {
        return manager.createQuery("select p from Product p where p.name = :name", Product.class)
                .setParameter("name",name)
                .getResultList();
    }
}
