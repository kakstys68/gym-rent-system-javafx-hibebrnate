package hibernate;

import model.Equipment;
import model.Gym;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class EquipmentHib {
        EntityManager entityManager = null;
        EntityManagerFactory entityManagerFactory = null;

        public EquipmentHib(EntityManagerFactory entityManagerFactory) {
            this.entityManagerFactory = entityManagerFactory;
        }

        //CREATE
        public void createEquipment(Equipment equipment){
            entityManager=entityManagerFactory.createEntityManager();
            try{
                entityManager.getTransaction().begin();
                entityManager.persist(equipment);
                entityManager.getTransaction().commit();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //READ

        public List<Equipment> getAllEquipment() {
            entityManager = entityManagerFactory.createEntityManager();
            try {
                CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
                query.select(query.from(Equipment.class));
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
        public void updateEquipment(Equipment equipment){
            entityManager=entityManagerFactory.createEntityManager();
            try{
                entityManager.getTransaction().begin();
                entityManager.merge(equipment);
                entityManager.getTransaction().commit();

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (entityManager != null) entityManager.close();
            }

        }

        public Object getEquipmentById(int id) {
            entityManager= entityManagerFactory.createEntityManager();
            Equipment equipment = null;
            try{
                entityManager.getTransaction().begin();
                equipment = entityManager.find(Equipment.class, id);
                entityManager.getTransaction().commit();
            }catch (Exception e){
                e.printStackTrace();
            }
            return  equipment;
        }

        //DELETE
        public void deleteEquipment(Equipment equipment){
            entityManager=entityManagerFactory.createEntityManager();

            try{
                entityManager.getTransaction().begin();
                entityManager.remove(entityManager.contains(equipment) ? equipment : entityManager.merge(equipment));
                entityManager.getTransaction().commit();

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (entityManager != null) entityManager.close();
            }

        }

}
