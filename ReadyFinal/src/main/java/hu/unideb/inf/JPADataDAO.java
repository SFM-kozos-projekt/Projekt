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
public class JPADataDAO implements DataDAO {

    final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("br.com.fredericci.pu");
    final EntityManager entityManager = entityManagerFactory.createEntityManager();
    
    @Override
    public void saveData(Data d) {
        entityManager.getTransaction().begin();
        entityManager.persist(d);
        entityManager.getTransaction().commit();
    }

    @Override
    public void deleteData(Data d) {
        entityManager.getTransaction().begin();
        entityManager.remove(d);
        entityManager.getTransaction().commit();
    }

    @Override
    public void updateData(Data d) {
        saveData(d);
    }

    @Override
    public List<Data> getData() {
        TypedQuery<Data> query = entityManager.createQuery("SELECT a FROM Data a", Data.class);
        List<Data> datas = query.getResultList();
        return datas;
    }

    @Override
    public void close() throws Exception {
        entityManager.close();
        entityManagerFactory.close();
        System.out.println("DataDAO closed...");
    }

    
}
