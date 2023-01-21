package hibernate;

import model.Client;
import model.RentProvider;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserHib {
    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;

    public UserHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    //CREATE
    public void createUser(User user){
        entityManager=entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //UPDATE
    public void updateUser(User user){
        entityManager=entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }

    }
    //READ
    public User getUserById(int id){
        entityManager= entityManagerFactory.createEntityManager();
        User user = null;
        try{
            entityManager.getTransaction().begin();
            user = entityManager.find(User.class, id);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public User getUserByLoginData(String username, String password, boolean isRentProvider){
        Query q = null;
        CriteriaQuery<Client> queryClient =null;
        CriteriaQuery<RentProvider> queryRentProvider =null;
        entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        if (!isRentProvider){
            queryClient = cb.createQuery(Client.class);
            Root<Client> root = queryClient.from(Client.class);
            queryClient.select(root).where(cb.and(cb.like(root.get("username"), username)),(cb.like(root.get("password"), password)));
        }else{
            queryRentProvider = cb.createQuery(RentProvider.class);
            Root<RentProvider> root = queryRentProvider.from(RentProvider.class);
            queryRentProvider.select(root).where(cb.and(cb.like(root.get("username"), username)),(cb.like(root.get("password"), password)));
        }
        try{
            if(queryClient != null)  q = entityManager.createQuery(queryClient);
            if(queryRentProvider != null)  q = entityManager.createQuery(queryRentProvider);
            return (User) q.getSingleResult();
        }catch (NoResultException e){
            return null;
        }

    }
    public List<Client> getAllClients() {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Client.class));
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

    public List<RentProvider> getAllRentProviders() {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(RentProvider.class));
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

    public void deleteUser(User user){
        entityManager=entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }

    }


}
