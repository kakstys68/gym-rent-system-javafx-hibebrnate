package hibernate;

import model.Comment;
import model.Equipment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CommentHib {
    EntityManager entityManager = null;
    EntityManagerFactory entityManagerFactory = null;

    public CommentHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    //CREATE
    public void createComment(Comment comment){
        entityManager=entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(comment);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //READ

    public List<Comment> getAllComments() {
        entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Comment.class));
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
    public void updateComment(Comment comment){
        entityManager=entityManagerFactory.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(comment);
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }

    }

    public Object getCommentById(int id) {
        entityManager= entityManagerFactory.createEntityManager();
        Comment comment = null;
        try{
            entityManager.getTransaction().begin();
            comment = entityManager.find(Comment.class, id);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  comment;
    }

    //DELETE
    public void deleteComment(Comment comment){
        entityManager=entityManagerFactory.createEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(comment) ? comment : entityManager.merge(comment));
            entityManager.getTransaction().commit();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (entityManager != null) entityManager.close();
        }

    }

}
