package dataaccess;

//import com.sun.istack.internal.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.Category;
import servlets.InventoryServlet;

public class CategoryDB {

    public Category get(int categoryId) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Category cat = em.find(Category.class, categoryId);
            return cat;
        } finally {
            em.close();
        }
    }

    public List<Category> getAll() {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Category> categorys = em.createNamedQuery("Category.findAll", Category.class).getResultList();
            return categorys;
        } finally {
            em.close();
        }
    }

    public boolean addCat(Category cat) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(cat);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
//            Logger.getLogger(InventoryServlet.class).log(Level.SEVERE, "Cannot add category", ex);
            return false;
        } finally {
            em.close();
        }
    }

    public boolean updateCat(Category editCat) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(editCat);
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
//            Logger.getLogger(InventoryServlet.class).log(Level.SEVERE, "Cannot edit category", ex);
            return false;
        } finally {
            em.close();
        }
    }

    public boolean deleteCat(Category deleteCat) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.remove(em.merge(deleteCat));
            trans.commit();
            return true;
        } catch (Exception ex) {
            trans.rollback();
//            Logger.getLogger(InventoryServlet.class).log(Level.SEVERE, "Cannot delete category", ex);
            return false;
        } finally {
            em.close();
        }
    }
}
