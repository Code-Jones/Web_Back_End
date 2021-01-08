package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.User;

/**
 * Class responsible for performing SQL commands on the database in relation to Users
 */
public class UserDB {

    /**
     * Method that returns all rows in the User table
     * @return a List of Users
     * @throws Exception if there is a Exception with PreparedStatements and ResultSets
     */
    public List<User> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
           List<User> users = em.createNamedQuery("User.findAll", User.class).getResultList();
           return users;
        } finally {
            em.close();         
        }
    }

    /**
     * Method for getting a row from the User database based off their email
     * @param email the email to of the User to return
     * @return the User that matches the email
     * @throws Exception if there is a Exception with PreparedStatements and ResultSets
     */
    public User get(String email) throws Exception {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
           User user = em.find(User.class, email);           
           return user;
        } finally {
            em.close();         
        }
    }

    /**
     * Method that inserts a new User into the User table
     * @param user the user that is to be inserted
     * @throws Exception if there is a Exception with PreparedStatements and ResultSets
     */
    public void insert(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.persist(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    /**
     * Method to update a User in the User table
     * @param user the new values for the user to be updated, updates based on a matching email
     * @throws Exception if there is a Exception with PreparedStatements and ResultSets
     */
    public void update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(user);
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }

    /**
     * Method to delete a User from the User table
     * @param user the user to be deleted
     * @throws Exception if there is a Exception with PreparedStatements and ResultSets
     */
    public void delete(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.remove(em.merge(user));
            trans.commit();
        } catch (Exception ex) {
            trans.rollback();
        } finally {
            em.close();
        }
    }
}
