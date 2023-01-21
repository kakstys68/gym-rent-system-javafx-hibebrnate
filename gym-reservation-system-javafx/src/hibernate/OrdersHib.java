package hibernate;

import model.Comment;
import model.Orders;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

public class OrdersHib {
    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;

    public OrdersHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void createOrder(Orders orders){
        entityManager=entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(orders);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<Orders> getAllOrders() {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Orders.class));
            Query q = entityManager.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return new ArrayList<>();
    }

    //UPDATE
    public void updateOrders(Orders orders){
        entityManager=entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(orders);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }

    }

    public Object getOrderById(int id) {
        entityManager= entityManagerFactory.createEntityManager();
        Orders orders = null;
        try{
            entityManager.getTransaction().begin();
            orders = entityManager.find(Orders.class, id);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return orders;
    }

    //DELETE
    public void deleteOrders(Orders orders){
        entityManager=entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(orders) ? orders : entityManager.merge(orders));
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }

    }
}
