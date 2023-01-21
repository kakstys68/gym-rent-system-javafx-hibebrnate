package hibernate;

import model.Client;
import model.Gym;
import model.RentProvider;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class GymHib {
    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;
    public GymHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    //CREATE
    public void createGym(Gym gym){
        entityManager=entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(gym);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //READ

    public List<Gym> getAllGyms() {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Gym.class));
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
    public void updateGym(Gym gym){
        entityManager=entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(gym);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }

    }

    public Object getGymById(int id) {
        entityManager= entityManagerFactory.createEntityManager();
        Gym gym = null;
        try{
            entityManager.getTransaction().begin();
            gym = entityManager.find(Gym.class, id);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  gym;
    }

    //DELETE
    public void deleteGym(Gym gym){
        entityManager=entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(gym) ? gym : entityManager.merge(gym));
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }

    }
}
