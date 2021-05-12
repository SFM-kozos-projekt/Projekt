/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.inf;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author gabor
 */
public class JPACarDAO implements CarDAO {

    final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.com.fredericci.pu");
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    
    @Override
    public void saveCar(Car c) {
        entityManager.getTransaction().begin();
        entityManager.persist(c);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteCar(Car c) {
        entityManager.getTransaction().begin();
        entityManager.remove(c);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateCar(Car c) {
        saveCar(c);
    }

    @Override
    public List<Car> getCar() {
        TypedQuery<Car> query = entityManager.createQuery("SELECT a FROM Car a", Car.class);
        List<Car> cars = query.getResultList();
        return cars;    
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
        System.out.println("CarDAO closed...");
    }
    
}
