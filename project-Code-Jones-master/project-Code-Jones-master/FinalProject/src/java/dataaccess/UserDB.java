package dataaccess;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.User;
import utilities.PasswordAuth;

public class UserDB {

    public User login(String email, String password) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            User user = get(email);
            if (password.equals(user.getPassword())) {
                return user;
            }
        } finally {
            em.close();
        }
        return null;
    }
    // my salted login, why it's not working beyond me. tomcat is having issues and org.apache.catalina.realm.LockOutRealm authenticate.
//    public User login(String email, String password) {
//        EntityManager em = DBUtil.getEmFactory().createEntityManager();
//        PasswordAuth pa = new PasswordAuth();
//
//        try {
//            User user = get(email);
//            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, password);
//            
//            String salt = user.getSalt();
//            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, salt);
//            
//            // i know this is not supposed to be here but you needed to keep the first 4 examples the same
//            if (salt.equals("password")) {
//                if (password.equals(user.getPassword())) {
//                    return user;
//                }
//            } 
//            if (!salt.isEmpty() || salt != null) {
//                String salted = pa.hashAndSaltPassword(password, salt);
//                Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, salted);
//                String pass = user.getPassword();
//                if (salted.equals(pass)) {
//                    return user;
//                }
//            }
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            em.close();
//        }
//
//        return null;
//    }

    public User get(String email) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            User user = em.find(User.class, email);
            return user;
        } finally {
            em.close();
        }
    }

    public List<User> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            List<User> users = em.createNamedQuery("User.findAll", User.class
            ).getResultList();
            return users;
        } finally {
            em.close();
        }
    }

    public boolean addUser(User user) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(user);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean update(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(user);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public int delete(User user, String sessionEmail) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        if (user.getEmail().equals(sessionEmail)) {
            return -1;
        } else if (user.getRole().getRoleId() != 2) { // add active? 
            return -2;
        } else {

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
        return 1;
    }

    public User getByUUID(String verification) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            User user = em.createNamedQuery("User.findByResetPasswordUUID", User.class
            ).setParameter("resetPasswordUUID", verification).getSingleResult();
            return user;
        } finally {
            em.close();
        }
    }
}
